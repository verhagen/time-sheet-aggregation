package com.github.verhagen.timesheet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivityExtractor implements TimeSheetEntryVisitor {
	private Logger logger = LoggerFactory.getLogger(ActivityExtractor.class);
	private Map<String, Set<String>> activities = new HashMap<>();
	private static String word = "[a-zA-Z-]+";
	private Pattern skipPattern = Pattern.compile("^\\(.*\\)$");
	private Pattern activityPattern = Pattern.compile("^(" +  word + ")\\[(" + word + ")\\]$");
	private List<Pattern> patternsToIgnore = new ArrayList<>();


	public ActivityExtractor() {
		this(null);
	}

	public ActivityExtractor(List<String> activitiesToIgnore) {
		if (activitiesToIgnore == null) {
			return;
		}
		for (String expression : activitiesToIgnore) {
			expression = toRegularExpression(expression);
			logger.info("Adding pattern to ignore: " + expression);
			patternsToIgnore.add(Pattern.compile(expression));
		}
	}

	public static String toRegularExpression(String expression) {
		expression = expression.replace("[", "\\[").replace("]", "\\]");
		expression = expression.replace("*", word);
		expression = "^" + expression + "$";
		return expression;
	}


	@Override
	public void visit(TimeSheetEntry entry) {
		String activityCln = StringUtils.trimToNull(entry.getActivity());
		if (activityCln == null) {
			return;
		}
		Matcher matcher = skipPattern.matcher(activityCln);
		if (matcher.matches()) {
			logger.info("Found skip activity '" + activityCln + "'");
			
			return;
		}
		for (Pattern patternToIgnore : patternsToIgnore) {
			if (patternToIgnore.matcher(activityCln).matches()) {
				logger.info("Ignore activity '" + activityCln + "'");
				return;
			}
		}
		matcher = activityPattern.matcher(activityCln);
		if (! matcher.matches()) {
			logger.warn("Found activity '" + activityCln + "' which does not follow the pattern '"
					+ activityPattern.pattern() + "'");
			return;
		}
		logger.info("group count: "  + matcher.groupCount());

		String mainActivity = matcher.group(1);
		String subActivity = matcher.group(2);
		if (! activities.containsKey(mainActivity)) {
			activities.put(mainActivity, new HashSet<>());
		}
		activities.get(mainActivity).add(subActivity);
	}


	public Set<String> getMainActivities() {
		return Collections.unmodifiableSet(activities.keySet());
	}


	public Set<String> getActivities(String mainActivity) {
		return Collections.unmodifiableSet(activities.get(mainActivity));
	}

}

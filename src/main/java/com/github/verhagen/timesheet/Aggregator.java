package com.github.verhagen.timesheet;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Aggregator implements TimeSheetVisitor {
	private static Logger logger = LoggerFactory.getLogger(Aggregator.class);
	private Map<LocalDate, Map<String, Float>> totalsPerMainActivityPerDay  = new LinkedHashMap<>();
	private Map<Pattern, String> groupBy = new HashMap<>();


	public Aggregator() {
		this(null);
	}
	
	public Aggregator(Map<String, String> groupByPatternAsStr) {
		for (Map.Entry<String, String> entry : groupByPatternAsStr.entrySet()) {
			String regExp = ActivityExtractor.toRegularExpression(entry.getKey());
			groupBy.put(Pattern.compile(regExp), entry.getValue());
		}
	}





	@Override
	public void visit(LocalDate date, List<Activity> entries) {
		if (! totalsPerMainActivityPerDay.containsKey(date)) {
			totalsPerMainActivityPerDay.put(date, new HashMap<>());
		}
		Map<String, Float> mainActivities = totalsPerMainActivityPerDay.get(date);
		for (Activity entry : entries) {
			String groupByActivity = groupBy(entry.getName());
			float groupByTotalHours = entry.getHours();
			if (mainActivities.containsKey(groupByActivity)) {
				groupByTotalHours += mainActivities.get(groupByActivity);
			}
			mainActivities.put(groupByActivity, groupByTotalHours);
		}
		
		
	}

	private String groupBy(String activity) {
		for (Map.Entry<Pattern, String> entry : groupBy.entrySet()) {
			if (entry.getKey().matcher(activity).matches()) {
				logger.info(entry.getKey().pattern() + "  " + activity + "  ->  " + entry.getValue());
				return entry.getValue();
			}
		}
		return "unknown";
	}

	public void accept(AggregateVisitor visitor) {
		for (Map.Entry<LocalDate, Map<String, Float>> entry : totalsPerMainActivityPerDay.entrySet()) {
			visitor.visit(entry.getKey(), entry.getValue());
		}
	}
	
}

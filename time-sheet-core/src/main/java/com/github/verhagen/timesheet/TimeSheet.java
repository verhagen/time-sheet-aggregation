package com.github.verhagen.timesheet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSheet implements ActivitiesPerDay {
	private static Logger logger = LoggerFactory.getLogger(TimeSheet.class);
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	private final List<Activity> entries;
	private final Map<LocalDate, List<Activity>> activitiesPerDay;
	private final Map<String, Week> weeks = new LinkedHashMap<>();


	public TimeSheet(Builder bldr) {
		entries = bldr.getEntries();
		activitiesPerDay = new HashMap<>();
		for (Activity entry : entries) {
			if (! activitiesPerDay.containsKey(entry.getDate())) {
				activitiesPerDay.put(entry.getDate(), new ArrayList<>());
			}
			activitiesPerDay.get(entry.getDate()).add(entry);

			String yearMonthIndex = Week.Builder.getYearWeek(entry.getDate());
			if (! weeks.containsKey(yearMonthIndex)) {
				weeks.put(yearMonthIndex, new Week(this, entry.getDate()));
			}
		}

//		Map<String, Week.Builder> weekBldrs = new LinkedHashMap<>();
//		for (LocalDate date :  activitiesPerDay.keySet()) {
//			String yearWeekIndex = Week.Builder.getYearWeek(date);
//			if (! weekBldrs.containsKey(yearWeekIndex)) {
//				weekBldrs.put(yearWeekIndex, new Week.Builder());
//			}
//			Week.Builder weekBldr = weekBldrs.get(yearWeekIndex);
//			weekBldr.add(date, activitiesPerDay.get(date));
//		}

	}


	public void accept(TimeSheetVisitor visitor, LocalDate startDate, LocalDate endDate) {
		for (Map.Entry<LocalDate, List<Activity>> entry : activitiesPerDay.entrySet()) {
			if (entry.getKey().isBefore(startDate) || entry.getKey().isAfter(endDate)) {
				logger.info("Entry with date '" + entry.getKey().format(formatter) + "' not in scope ("
						+ startDate.format(formatter ) + " - " + endDate.format(formatter) + ").");
				continue;
			}
			visitor.visit(entry.getKey(), entry.getValue());
		}
	}

	public void accept(Visitor<Activity> visitor) {
		for (Activity entry : entries) {
			entry.accept(visitor);
		}
	}

	public List<Activity> getActivities(LocalDate date) {
		return activitiesPerDay.get(date);
	}




	public static class Builder {
		private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		private List<Activity> activities = new ArrayList<>();


		public Builder add(String dateStr, float hours, String activity) {
			add(new Activity.Builder()
				.add(LocalDate.parse(dateStr, formatter))
				.add(hours)
				.add(activity)
				.create());
			return this;
		}
		public Builder add(String dateStr, float hours, String activity, String description) {
			add(new Activity.Builder()
					.add(LocalDate.parse(dateStr, formatter))
					.add(hours)
					.add(activity)
					.add(description)
					.create());
			return this;
		}

		public List<Activity> getEntries() {
			return activities;
		}

		private Builder add(Activity entry) {
			activities.add(entry);
			return this;
		}

		public TimeSheet create() {
			return new TimeSheet(this);
		}
	}

}

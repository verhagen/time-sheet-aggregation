package com.github.verhagen.timesheet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSheet implements ActivitiesPerDay {
	private static Logger logger = LoggerFactory.getLogger(TimeSheet.class);
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	private final List<Activity> activities;
	private final Map<LocalDate, List<Activity>> activitiesPerDay;
	private final Map<String, Week> weeks = new LinkedHashMap<>();
	private final Node<String, LocalDate, Activity> activityTree = new Node<>("__root__");


	public TimeSheet(Builder bldr) {
		activities = bldr.getEntries();
		activitiesPerDay = new HashMap<>();
		for (Activity activity : activities) {
			addActivitiesPerDay(activity);
			addWeeksPerYear(activity.getDate());
			activityTree.add(activity.getNamePath(), activity.getDate(), activity);
		}
	}

	private void addActivitiesPerDay(Activity entry) {
		if (! activitiesPerDay.containsKey(entry.getDate())) {
			activitiesPerDay.put(entry.getDate(), new ArrayList<>());
		}
		activitiesPerDay.get(entry.getDate()).add(entry);
	}

	private void addWeeksPerYear(LocalDate date) {
		String yearMonthIndex = Week.getYearWeek(date);
		if (! weeks.containsKey(yearMonthIndex)) {
			weeks.put(yearMonthIndex, new Week(this, date));
		}
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

	public List<Activity> getActivities() {
		return activities;
	}
	public List<Activity> getActivities(LocalDate date) {
		return activitiesPerDay.get(date);
	}
	public Week getWeek(String yearWeek) {
		return weeks.get(yearWeek);
	}
	public List<Activity> getActivities(String yearWeek, DayOfWeek day) {
		return activitiesPerDay.get(weeks.get(yearWeek).getDate(day));
	}

	public Map<LocalDate, List<Activity>> getActivities(String pathName) {
		return activityTree.getEntries(pathName.split("\\."));
	}
	public List<Activity> getActivities(String pathName, LocalDate date) {
		return activityTree.getEntries(pathName.split("\\.")).get(date);
	}

	public Set<String> getWeeks() {
		return weeks.keySet();
	}

	public Set<LocalDate> getDays() {
		return activitiesPerDay.keySet();
	}


	public void accept(Visitor<Activity> visitor) {
		for (Activity activity : activities) {
			activity.accept(visitor);
		}
	}

	public void accept(Visitor<Activity> visitor, LocalDate date) {
		for (Activity activity : activitiesPerDay.get(date)) {
			activity.accept(visitor);
		}
	}

	public void accept(Visitor<Activity> visitor, LocalDate fromDate, LocalDate tillDate) {
		while (fromDate.isBefore(tillDate) || fromDate.isEqual(tillDate)) {
			accept(visitor, fromDate);
			fromDate = fromDate.plusDays(1);
		}
	}

	public void acceptForWeek(Visitor<Activity> visitor, int year, int week) {
	}

	public void acceptForMonth(Visitor<Activity> visitor, int year, int month) {
	}

	public void accept(Visitor<Activity> visitor, String pathName) {
		for (List<Activity> activitiesPerDay : activityTree.getEntries(pathName.split("\\.")).values()) {
			for (Activity activity : activitiesPerDay) {
				activity.accept(visitor);
			}
		}
	}

	public static class Builder {
		private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		private List<Activity> activities = new ArrayList<>();


		public Builder add(String dateStr, int minutes, String activity) {
			add(new Activity.Builder()
				.add(LocalDate.parse(dateStr, formatter))
				.add(minutes)
				.add(activity)
				.create());
			return this;
		}
		public Builder add(String dateStr, int minutes, String activity, String description) {
			add(new Activity.Builder()
					.add(LocalDate.parse(dateStr, formatter))
					.add(minutes)
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

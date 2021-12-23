package com.github.verhagen.timesheet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Week implements ActivitiesPerDay {
	private static WeekFields weekFields = WeekFields.ISO;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	private final Map<DayOfWeek, LocalDate> daysOfWeek = new HashMap<>();
	private final String yearWeek;
	private final ActivitiesPerDay activitiesPerDay;


	public Week(ActivitiesPerDay activitiesPerDay, LocalDate date) {
		this.activitiesPerDay = activitiesPerDay;
		yearWeek = getYearWeek(date);
		LocalDate monday = getMonday(date);
		for (DayOfWeek day : DayOfWeek.values()) {
			LocalDate dayDate = monday.plusDays(day.getValue() - 1); // Monday has value 1, 
			daysOfWeek.put(day, dayDate);
		}
	}


	private LocalDate getMonday(LocalDate  date) {
		switch (date.getDayOfWeek()) {
		case MONDAY:
			return date;
		case TUESDAY:
			return date.minusDays(1);
		case WEDNESDAY:
			return date.minusDays(2);
		case THURSDAY:
			return date.minusDays(3);
		case FRIDAY:
			return date.minusDays(4);
		case SATURDAY:
			return date.minusDays(5);
		case SUNDAY:
			return date.minusDays(6);
		}
		return null;
	}


	public void accept(Visitor<Week> visitor) {
		visitor.visit(this);
	}


	public String getYearWeek() {
		return yearWeek;
	}

	public LocalDate getDate(DayOfWeek day) {
		return daysOfWeek.get(day);
	}

	public static String getYearWeek(LocalDate date) {
		int week = date.get(weekFields.weekOfWeekBasedYear());
		int year = date.getYear();
		return year + "." + (week < 10 ? "0" + week : week);
	}

	@Override
	public List<Activity> getActivities(LocalDate date) {
		return activitiesPerDay.getActivities(date);
	}

	public List<Activity> getActivities(DayOfWeek day) {
		return getActivities(getDate(day));
	}

	
//	public static class Builder {
//		private Integer week;
//		private Integer year;
//		private Map<LocalDate, Map<String, Float>> weekEntries = new LinkedHashMap<>();
//		private String yearWeek;
//
//
//		public String getYearWeek() {
//			return yearWeek;
//		}
//
//
//		public Week create() {
//			return null;//new Week(this);
//		}
//
//
//		public Map<LocalDate, Map<String, Float>> getWeekEntries() {
//			return weekEntries;
//		}
//
//
//		public int getWeek() {
//			return week;
//		}
//
//		public Builder add(LocalDate date) {
//			if (week == null) {
//				week = date.get(weekFields.weekOfWeekBasedYear());
//				year = date.getYear();
//				yearWeek = getYearWeek(date);
//			}
//			return this;
//		}
//
//		public void add(LocalDate date, List<Activity> entries) {
//			if (week == null) {
//				week = date.get(weekFields.weekOfWeekBasedYear());
//				year = date.getYear();
//				yearWeek = getYearWeek(date);
//			}
//			else if (week != date.get(weekFields.weekOfWeekBasedYear())
//					|| year != date.getYear()) {
//				throw new IllegalArgumentException("Argument 'date' with value '" + date.format(formatter)
//					+ "' doesn't belong to this week '" + week + "' of year '" + year + "'");
//			}
//
////			weekEntries.put(date, entries);
//		}
//
//		public static String getYearWeek(LocalDate date) {
//			int week = date.get(weekFields.weekOfWeekBasedYear());
//			int year = date.getYear();
//			return year + "." + (week < 10 ? "0" + week : week);
//		}
//
//	}




}

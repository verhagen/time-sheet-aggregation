package com.github.verhagen.timesheet;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Week {
	private static WeekFields weekFields = WeekFields.ISO;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	private final Map<LocalDate, Map<String, Float>> weekEntries = new LinkedHashMap<>();
	private final Map<DayOfWeek, LocalDate> daysOfWeek = new HashMap<>();
	private final String yearWeek;


	public Week(Builder bldr) {
		this.weekEntries.putAll(bldr.getWeekEntries());
		this.yearWeek = bldr.getYearWeek();
		LocalDate monday = getMonday();
		for (DayOfWeek day : DayOfWeek.values()) {
			LocalDate dayDate = monday.plusDays(day.getValue() - 1); // Monday has value 1, 
			daysOfWeek.put(day, dayDate);
		}
	}


	private LocalDate getMonday() {
		LocalDate date = weekEntries.keySet().iterator().next();
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

	public Map<String, Float> getActivities(DayOfWeek day) {
		if (! weekEntries.containsKey(daysOfWeek.get(day))) {
			return Collections.emptyMap();
		}
		return weekEntries.get(daysOfWeek.get(day));
	}

	public LocalDate getDate(DayOfWeek day) {
		return daysOfWeek.get(day);
	}



	
	public static class Builder {
		private Integer week;
		private Integer year;
		private Map<LocalDate, Map<String, Float>> weekEntries = new LinkedHashMap<>();
		private String yearWeek;


		public String getYearWeek() {
			return yearWeek;
		}


		public Week create() {
			return new Week(this);
		}


		public Map<LocalDate, Map<String, Float>> getWeekEntries() {
			return weekEntries;
		}


		public int getWeek() {
			return week;
		}

		public void add(LocalDate date, Map<String, Float> entries) {
			if (week == null) {
				week = date.get(weekFields.weekOfWeekBasedYear());
				year = date.getYear();
				yearWeek = getYearWeek(date);
			}
			else if (week != date.get(weekFields.weekOfWeekBasedYear())
					|| year != date.getYear()) {
				throw new IllegalArgumentException("Argument 'date' with value '" + date.format(formatter)
					+ "' doesn't belong to this week '" + week + "' of year '" + year + "'");
			}

			weekEntries.put(date, entries);
		}

		public static String getYearWeek(LocalDate date) {
			int week = date.get(weekFields.weekOfWeekBasedYear());
			int year = date.getYear();
			return year + "." + (week < 10 ? "0" + week : week);
		}

	}

}

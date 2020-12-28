package com.github.verhagen.timesheet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeSheetLogbook {
	private static Logger logger = LoggerFactory.getLogger(TimeSheetLogbook.class);
	private final List<TimeSheetEntry> entries;
	private final Map<LocalDate, List<TimeSheetEntry>> activities;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");


	public TimeSheetLogbook(Builder bldr) {
		entries = bldr.getEntries();
		activities = new HashMap<>();
		for (TimeSheetEntry entry : entries) {
			if (! activities.containsKey(entry.getDate())) {
				activities.put(entry.getDate(), new ArrayList<>());
			}
			activities.get(entry.getDate()).add(entry);
		}
	}


	public void accept(TimeSheetVisitor visitor, LocalDate startDate, LocalDate endDate) {
		for (Map.Entry<LocalDate, List<TimeSheetEntry>> entry : activities.entrySet()) {
			if (entry.getKey().isBefore(startDate) || entry.getKey().isAfter(endDate)) {
				logger.info("Entry with date '" + entry.getKey().format(formatter) + "' not in scope ("
						+ startDate.format(formatter ) + " - " + endDate.format(formatter) + ").");
				continue;
			}
			visitor.visit(entry.getKey(), entry.getValue());
		}
	}

	public void accept(TimeSheetEntryVisitor visitor) {
		for (TimeSheetEntry entry : entries) {
			entry.accept(visitor);
		}
	}


	public static class Builder {
		private List<TimeSheetEntry> entries = new ArrayList<>();
		private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");


		public Builder add(String dateStr, float hours, String activity) {
			add(new TimeSheetEntry.Builder()
				.add(LocalDate.parse(dateStr, formatter))
				.add(hours)
				.add(activity)
				.create());
			return this;
		}

		public List<TimeSheetEntry> getEntries() {
			return entries;
		}

		private Builder add(TimeSheetEntry entry) {
			entries.add(entry);
			return this;
		}

		public TimeSheetLogbook create() {
			return new TimeSheetLogbook(this);
		}

	}


	public class Activity {
		
	}
}

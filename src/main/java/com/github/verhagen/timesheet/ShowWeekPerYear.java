package com.github.verhagen.timesheet;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShowWeekPerYear implements Visitor<Week> {
	private Logger logger = LoggerFactory.getLogger(ShowWeekPerYear.class);
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
	private final static String COL_SEP = "; ";
	private List<String> weeksAsText = new ArrayList<>();
//	private List<String> filters = new ArrayList<>();


//	public ShowWeekPerYear(List<String> filters) {
//		this.filters .addAll(filters);
//	}

	@Override
	public void visit(Week week) {
		StringBuilder bldr = new StringBuilder();
		bldr.append(week.getYearWeek()).append(COL_SEP);
		for (DayOfWeek day : DayOfWeek.values()) {
			if (DayOfWeek.MONDAY == day) {
				bldr.append(week.getDate(day).format(formatter));
				bldr.append(" - ");
				bldr.append(week.getDate(day).plusDays(6).format(formatter));
				bldr.append(COL_SEP);
			}
			logger.info(week.getYearWeek() + "  " + day.name() + "  " + week.getActivities(day));

			// TODO sum the specific activities
			Float hours = week.getActivities(day).get("sample");
			bldr.append((hours == null ? "   " : hours)).append(COL_SEP);
		}
		weeksAsText.add(bldr.toString());
	}


	public String asText() {
		StringBuilder bldr = new StringBuilder();
		for (String text : weeksAsText) {
			if (bldr.length() > 0) {
				bldr.append("\n");
			}
			bldr.append(text);
		}
		return bldr.toString();
	}

	
}

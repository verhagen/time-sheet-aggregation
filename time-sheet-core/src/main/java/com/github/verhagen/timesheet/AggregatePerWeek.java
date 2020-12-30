package com.github.verhagen.timesheet;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregatePerWeek implements AggregateVisitor {
	private Logger logger = LoggerFactory.getLogger(AggregatePerWeek.class);
	private Map<String, Week.Builder> weekBldrs = new LinkedHashMap<>();
	private Map<String, Week> weeks = new LinkedHashMap<>();
	private boolean isLockedAlready = false;


	@Override
	public void visit(LocalDate date, Map<String, Float> entries) {
		if (isLockedAlready) {
			throw new RuntimeException("This AggregatePerWeek has already been locked.");
		}
		String yearWeekIndex = Week.Builder.getYearWeek(date);
		if (! weekBldrs.containsKey(yearWeekIndex)) {
			weekBldrs.put(yearWeekIndex, new Week.Builder());
		}
		Week.Builder bldr = weekBldrs.get(yearWeekIndex);
//		bldr.add(date, entries);
	}

	public void lock() {
		if (isLockedAlready) {
			return;
		}
		for (Map.Entry<String, Week.Builder> entry : weekBldrs.entrySet()) {
			weeks.put(entry.getKey(), entry.getValue().create());
		}
	}

	public void accept(Visitor<Week> visitor) {
		if (! isLockedAlready) {
			lock();
		}
		for (Week week : weeks.values()) {
			visitor.visit(week);
		}
	}

}

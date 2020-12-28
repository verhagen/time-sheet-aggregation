package com.github.verhagen.timesheet;

import java.time.LocalDate;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregatePerWeek implements AggregateVisitor {
	private Logger logger = LoggerFactory.getLogger(AggregatePerWeek.class);

	@Override
	public void visit(LocalDate date, Map<String, Float> entries) {
		for (Map.Entry<String, Float> entry : entries.entrySet()) {
			logger.info(date + "  " + entry.getKey() +  "  " + entry.getValue());
		}
	}

	
}

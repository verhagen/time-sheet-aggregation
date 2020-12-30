package com.github.verhagen.timesheet;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AggregatorPerWeekTest {
	private Logger logger = LoggerFactory.getLogger(AggregatorPerWeekTest.class);

	@Test
	void aggregateHoursPerDay() {
		Map<String, String> groupBy = new LinkedHashMap<>();
		groupBy.put("sample[travel]", "sample-travel");
		groupBy.put("sample[*]", "sample");
		groupBy.put("self[*]", "self");
				
		Aggregator aggregator = new Aggregator(groupBy);
		TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
		timeSheet.accept(aggregator, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 31));

		AggregatePerWeek aggregatePerWeek = new AggregatePerWeek();
		aggregator.accept(aggregatePerWeek);
	}

	@Test
	void aggregateHoursPerDayTwoWeeks() {
		Map<String, String> groupBy = new LinkedHashMap<>();
		groupBy.put("sample.travel.from", "sample-travel");
		groupBy.put("sample.travel.to", "sample-travel");
		groupBy.put("sample.travel", "sample-travel");
		groupBy.put("sample.*", "sample");
		groupBy.put("self.*", "self");
				
		Aggregator aggregator = new Aggregator(groupBy);
		TimeSheet timeSheet = TimeSheetHelper.createTimeSheetTwoWeeks();
		timeSheet.accept(aggregator, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 31));

		AggregatePerWeek aggregatePerWeek = new AggregatePerWeek();
		aggregator.accept(aggregatePerWeek);
		
		ShowWeekPerYear showWeekPerYear = new ShowWeekPerYear();
		aggregatePerWeek.accept(showWeekPerYear);
		logger.info("\n" + showWeekPerYear.asText());
	}

}

package com.github.verhagen.timesheet;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class AggregatorTest {


	@Test
	void aggregateHoursPerDay() {
		Map<String, String> groupBy = new LinkedHashMap<>();
		groupBy.put("sample[travel]", "sample-travel");
		groupBy.put("sample[*]", "sample");
		groupBy.put("self[*]", "self");
				
		Aggregator aggregator = new Aggregator(groupBy);
		TimeSheetLogbook timeSheet = TimeSheetHelper.createTimeSheetSimple();
		timeSheet.accept(aggregator, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 31));

		AggregatePerWeek aggregatePerWeek = new AggregatePerWeek();
		aggregator.accept(aggregatePerWeek);
	}

	@Test
	void aggregateHoursPerDayTwoWeeks() {
		Map<String, String> groupBy = new LinkedHashMap<>();
		groupBy.put("sample[travel-from]", "sample-travel");
		groupBy.put("sample[travel-to]", "sample-travel");
		groupBy.put("sample[travel]", "sample-travel");
		groupBy.put("sample[*]", "sample");
		groupBy.put("self[*]", "self");
				
		Aggregator aggregator = new Aggregator(groupBy);
		TimeSheetLogbook timeSheet = TimeSheetHelper.createTimeSheetTwoWeeks();
		timeSheet.accept(aggregator, LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 31));

		AggregatePerWeek aggregatePerWeek = new AggregatePerWeek();
		aggregator.accept(aggregatePerWeek);
	}

}

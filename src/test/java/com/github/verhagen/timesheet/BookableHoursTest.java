package com.github.verhagen.timesheet;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BookableHoursTest {

	@Test
	void bookbleHours() throws Exception {
		TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
		ActivityGraph activityExtractor = new ActivityGraph();
		timeSheet.accept(activityExtractor);
		List<String> activitiesToIgnore = Arrays.asList(new String [] {
				"*[travel]"
			});

//		BookableHoursVisitor visitor = new BookableHoursVisitor(activitiesToIgnore);
//		timeSheet.accept(visitor);
	}

}

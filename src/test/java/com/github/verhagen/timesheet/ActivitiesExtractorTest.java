package com.github.verhagen.timesheet;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivitiesExtractorTest {
	private Logger logger = LoggerFactory.getLogger(ActivitiesExtractorTest.class);


	@Test
	void extractActivities() {
		TimeSheetLogbook timeSheet = TimeSheetHelper.createTimeSheetSimple();

		ActivityExtractor activityExtractor = new ActivityExtractor();
		timeSheet.accept(activityExtractor);
		Set<String> mainActivities = activityExtractor.getMainActivities();

		assertNotNull(mainActivities);
		assertEquals(1, mainActivities.size());
		Set<String> subActivities = activityExtractor.getActivities("sample");
		assertNotNull(subActivities);
		assertEquals(3, subActivities.size());
		assertTrue(subActivities.contains("programming"));
		assertTrue(subActivities.contains("testing"));
		assertTrue(subActivities.contains("travel"));
	}

	@Test
	void extractActivitiesWithOutTravel() {
		TimeSheetLogbook timeSheet = TimeSheetHelper.createTimeSheetSimple();

		List<String> activitiesToIgnore = Arrays.asList(new String [] {
			"*[travel]"
		});
		ActivityExtractor activityExtractor = new ActivityExtractor(activitiesToIgnore);
		timeSheet.accept(activityExtractor);
		Set<String> mainActivities = activityExtractor.getMainActivities();

		assertNotNull(mainActivities);
		assertEquals(1, mainActivities.size());
		Set<String> subActivities = activityExtractor.getActivities("sample");
		assertNotNull(subActivities);
		assertEquals(2, subActivities.size());
		assertTrue(subActivities.contains("programming"));
		assertTrue(subActivities.contains("testing"));
		assertFalse(subActivities.contains("travel"));
	}

	@Test
	void extractActivitiesAll() {
		TimeSheetLogbook timeSheet = TimeSheetHelper.createTimeSheetTwoWeeks();

//		List<String> activitiesToIgnore = Arrays.asList(new String [] {
//			"*[travel*]"
//			, "self[*]"
//		});
		ActivityExtractor activityExtractor = new ActivityExtractor();
		timeSheet.accept(activityExtractor);
		Set<String> mainActivities = activityExtractor.getMainActivities();

		assertNotNull(mainActivities);
		assertEquals(2, mainActivities.size());
		assertTrue(mainActivities.contains("sample"));
		assertTrue(mainActivities.contains("self"));
		
		Set<String> subActivities = activityExtractor.getActivities("sample");
		assertNotNull(subActivities);
		assertEquals(8, subActivities.size());
		assertTrue(subActivities.contains("programming"));
		assertTrue(subActivities.contains("testing"));
		assertTrue(subActivities.contains("travel"));
		logger.info("sample: activities: " + subActivities);

		subActivities = activityExtractor.getActivities("self");
		assertNotNull(subActivities);
		assertEquals(2, subActivities.size());
		assertTrue(subActivities.contains("study"));
		assertTrue(subActivities.contains("administration"));
	}

}

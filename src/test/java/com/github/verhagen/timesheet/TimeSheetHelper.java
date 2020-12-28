package com.github.verhagen.timesheet;

public class TimeSheetHelper {

	public static TimeSheetLogbook createTimeSheetSimple() {
		TimeSheetLogbook.Builder bldr = new TimeSheetLogbook.Builder();

//		2020; 01; Fri; 2020.01.03; 8; test[programming]
//		2020; 01; Thu; 2020.01.02; 4; test[programming]
//		2020; 01; Thu; 2020.01.02; 4; test[testing]
//		                         ; 2; test[travel]
//		2020; 01; Wed; 2020.01.01; ; (New Year's Day)		
		bldr.add("2020.01.03", 8f, "sample[programming]");
		bldr.add("2020.01.02", 4f, "sample[programming]");
		bldr.add("2020.01.02", 4f, "sample[testing]");
		bldr.add("2020.01.02", 2f, "sample[travel]");
		bldr.add("2020.01.01", 0f, "(New Year's Day)");
		TimeSheetLogbook timeSheet =  bldr.create();
		return timeSheet;
	}

	public static TimeSheetLogbook createTimeSheetTwoWeeks() {
		TimeSheetLogbook.Builder bldr = new TimeSheetLogbook.Builder();

//		2020; 02; Fri; 2020.01.10; 8; sample[programming]
//		                         ; 1; sample[travel-from]
//		2020; 02; Thu; 2020.01.09; 8; sample[programming]
//		2020; 02; Wed; 2020.01.08; 8; sample[programming]
//		2020; 02; Tue; 2020.01.07; 4; sample[programming]
//		                         ; 1; sample[scrum-sprint-retrospective]
//		                         ; 1; sample[scrum-sprint-review]
//		                         ; 2; sample[scrum-sprint-planning]
//		2020; 02; Mon; 2020.01.06; 6; sample[programming]
//		                         ; 1; sample[travel-to]
//		                         ; 1; self[study]
//		                         ; 1; self[administration]
		bldr.add("2020.01.10", 8f, "sample[programming]");
		bldr.add("2020.01.10", 1f, "sample[travel-from]");
		bldr.add("2020.01.09", 8f, "sample[programming]");
		bldr.add("2020.01.08", 8f, "sample[programming]");
		bldr.add("2020.01.07", 4f, "sample[programming]");
		bldr.add("2020.01.07", 1f, "sample[scrum-sprint-retrospective]");
		bldr.add("2020.01.07", 1f, "sample[scrum-sprint-review]");
		bldr.add("2020.01.07", 2f, "sample[scrum-sprint-planning]");
		bldr.add("2020.01.06", 6f, "sample[programming]");
		bldr.add("2020.01.06", 1f, "sample[travel-to]");
		bldr.add("2020.01.06", 1f, "self[study]");
		bldr.add("2020.01.06", 1f, "self[administration]");
		
		
//		2020; 01; Fri; 2020.01.03; 8; sample[programming]
//		2020; 01; Thu; 2020.01.02; 4; sample[programming]
//		2020; 01; Thu; 2020.01.02; 4; sample[testing]
//		                         ; 2; sample[travel]
//		2020; 01; Wed; 2020.01.01; ; (New Year's Day)		
		bldr.add("2020.01.03", 8f, "sample[programming]");
		bldr.add("2020.01.02", 4f, "sample[programming]");
		bldr.add("2020.01.02", 4f, "sample[testing]");
		bldr.add("2020.01.02", 2f, "sample[travel]");
		bldr.add("2020.01.01", 0f, "(New Year's Day)");
		TimeSheetLogbook timeSheet =  bldr.create();
		return timeSheet;
	}

}

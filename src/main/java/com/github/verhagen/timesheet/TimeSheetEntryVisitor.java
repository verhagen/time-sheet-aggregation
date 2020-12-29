package com.github.verhagen.timesheet;

public interface TimeSheetEntryVisitor {

	public void visit(Activity activity);

}

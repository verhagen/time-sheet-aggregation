package com.github.verhagen.timesheet;

import java.time.LocalDate;
import java.util.List;

public interface TimeSheetVisitor {

	void visit(LocalDate key, List<Activity> value);

}

package com.github.verhagen.timesheet;

import java.time.LocalDate;
import java.util.Map;

public interface AggregateVisitor {

	void visit(LocalDate key, Map<String, Float> value);

}

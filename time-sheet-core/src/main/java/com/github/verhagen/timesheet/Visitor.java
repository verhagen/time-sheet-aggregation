package com.github.verhagen.timesheet;

public interface Visitor<T> {

	void visit(T type);

}

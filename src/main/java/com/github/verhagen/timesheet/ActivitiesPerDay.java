package com.github.verhagen.timesheet;

import java.time.LocalDate;
import java.util.List;

public interface ActivitiesPerDay {

	List<Activity> getActivities(LocalDate date);

}

package com.github.verhagen.timesheet;

import java.util.List;

public class ActivityNode {
	private String name;
	private List<Activity> activities;

	public ActivityNode(String name) {
		if (name.contains(".")) {
			throw new IllegalArgumentException("Argument name should not contain a dot '.' character.");
		}
		this.name = name;
	}

	public void add(Activity activity) {
		activities.add(activity);
	}

	public float getHours() {
		float totalHours = 0;
		for (Activity activity : activities) {
			totalHours += activity.getHours();
		}
		return totalHours;
	}

}

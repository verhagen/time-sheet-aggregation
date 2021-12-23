package com.github.verhagen.timesheet;

import java.time.LocalDate;

public class Activity {
	private final LocalDate date;
	private final int minutes;
	private final String name;
	private final String description;


	public Activity(Builder bldr) {
		this.date = bldr.getDate();
		this.minutes = bldr.getMinutes();
		this.name = bldr.getName();
		this.description = bldr.getDescription();
	}


	public LocalDate getDate() {
		return date;
	}

	public int getMinutes() {
		return minutes;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}


	public void accept(Visitor<Activity> visitor) {
		visitor.visit(this);
	}

	public String[] getNamePath() {
		return name.split("\\.");
	}

	@Override
	public String toString() {
		return "Activity{" +
				"date: '" + date +
				"', minutes: '" + minutes +
				"', name: '" + name +
				"', description='" + description +
				"'}";
	}

	public static class Builder {
		private LocalDate date;
		private int minutes;
		private String name;
		private String description;


		public Activity create() {
			return new Activity(this);
		}


		public Builder add(LocalDate date) {
			this.date = date;
			return this;
		}

		public Builder add(int minutes) {
			this.minutes = minutes;
			return this;
		}

		public Builder add(String name) {
			this.name = name;
			return this;
		}

		public Builder addDescription(String description) {
			this.description = description;
			return this;
		}


		public LocalDate getDate() {
			return date;
		}

		public int getMinutes() {
			return minutes;
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}

	}

}

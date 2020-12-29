package com.github.verhagen.timesheet;

import java.time.LocalDate;

public class Activity {
	private final LocalDate date;
	private final float hours;
	private final String name;


	public Activity(Builder bldr) {
		this.date = bldr.getDate();
		this.hours = bldr.getHours();
		this.name = bldr.getName();
	}


	public LocalDate getDate() {
		return date;
	}

	public float getHours() {
		return hours;
	}

	public String getName() {
		return name;
	}


	public void accept(Visitor<Activity> visitor) {
		visitor.visit(this);
	}




	public static class Builder {
		private LocalDate date;
		private float hours;
		private String name;


		public Activity create() {
			return new Activity(this);
		}


		public Builder add(LocalDate date) {
			this.date = date;
			return this;
		}

		public Builder add(float hours) {
			this.hours = hours;
			return this;
		}

		public Builder add(String name) {
			this.name = name;
			return this;
		}


		public LocalDate getDate() {
			return date;
		}

		public float getHours() {
			return hours;
		}

		public String getName() {
			return name;
		}

	}

}

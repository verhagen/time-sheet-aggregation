package com.github.verhagen.timesheet;

import java.time.LocalDate;

public class TimeSheetEntry {
	private final LocalDate date;
	private final float hours;
	private final String activity;


	public TimeSheetEntry(Builder bldr) {
		this.date = bldr.getDate();
		this.hours = bldr.getHours();
		this.activity = bldr.getActivity();
	}


	public LocalDate getDate() {
		return date;
	}

	public float getHours() {
		return hours;
	}

	public String getActivity() {
		return activity;
	}


	public void accept(TimeSheetEntryVisitor visitor) {
		visitor.visit(this);
	}




	public static class Builder {
		private LocalDate date;
		private float hours;
		private String activity;


		public TimeSheetEntry create() {
			return new TimeSheetEntry(this);
		}


		public Builder add(LocalDate date) {
			this.date = date;
			return this;
		}

		public Builder add(float hours) {
			this.hours = hours;
			return this;
		}

		public Builder add(String activity) {
			this.activity = activity;
			return this;
		}


		public LocalDate getDate() {
			return date;
		}

		public float getHours() {
			return hours;
		}

		public String getActivity() {
			return activity;
		}

	}

}

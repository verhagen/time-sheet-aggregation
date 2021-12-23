package com.github.verhagen.timesheet;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeSheetTest {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @Test
    void getActivities() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        List<Activity> activities = timeSheet.getActivities();
        assertEquals(5, activities.size());
    }

    @Test
    void getDays() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        assertEquals(3, timeSheet.getDays().size());
    }

    @Test
    void getActivitiesPerDay() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        List<Activity> activities = timeSheet.getActivities(LocalDate.parse("2020.01.03", formatter));
        assertEquals(1, activities.size());
        activities = timeSheet.getActivities(LocalDate.parse("2020.01.02", formatter));
        assertEquals(3, activities.size());
        activities = timeSheet.getActivities(LocalDate.parse("2020.01.01", formatter));
        assertEquals(1, activities.size());
    }

    @Test
    void getWeeks() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        assertEquals(1, timeSheet.getWeeks().size());
    }

    @Test
    void getWeek() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        Set<String> weeks = timeSheet.getWeeks();
        assertTrue(weeks.contains("2020.01"));
        Week week = timeSheet.getWeek("2020.01");
        assertEquals("2020.01", week.getYearWeek());
    }

    @Test
    void getActivitiesPerWeekDay() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        List<Activity> activities = timeSheet.getActivities("2020.01", DayOfWeek.WEDNESDAY);
        assertEquals(1, activities.size());
        activities = timeSheet.getActivities("2020.01", DayOfWeek.THURSDAY);
        assertEquals(3, activities.size());
        activities = timeSheet.getActivities("2020.01", DayOfWeek.FRIDAY);
        assertEquals(1, activities.size());
    }

    @Test
    void getActivitiesByPathName() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        Map<LocalDate, List<Activity>> activitiesPerDay = timeSheet.getActivities("sample.programming");
        assertEquals(2, activitiesPerDay.size());
    }

    @Test
    void getActivitiesByPathNameAndDay() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        List<Activity> activities = timeSheet.getActivities("sample.programming", LocalDate.parse("2020.01.02", formatter));
        assertEquals(1, activities.size());
    }

    @Test
    void visitActivities() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        Visitor<Activity> visitor = new ActivityVisitor();
        timeSheet.accept(visitor);
    }

    @Test
    void visitActivitiesPerDay() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        Visitor<Activity> visitor = new ActivityVisitor();
        LocalDate date = LocalDate.parse("2020.01.02", formatter);
        timeSheet.accept(visitor, date);
    }

    @Test
    void visitActivitiesFromTillDay() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        Visitor<Activity> visitor = new ActivityVisitor();
        LocalDate fromDate = LocalDate.parse("2020.01.02", formatter);;
        LocalDate tillDate = LocalDate.parse("2020.01.03", formatter);;
        timeSheet.accept(visitor, fromDate, tillDate);
    }

    @Test
    void visitActivitiesPerWeek() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        Visitor<Activity> visitor = new ActivityVisitor();
        int year = 2020;
        int week = 1;
        timeSheet.acceptForWeek(visitor, year, week);
    }

    @Test
    void visitActivitiesPerMonth() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        Visitor<Activity> visitor = new ActivityVisitor();
//        LocalDate fromDate = null;
//        LocalDate tillDate = null;
        int year = 2020;
        int month = 1;
        timeSheet.acceptForMonth(visitor, year, month);
    }

    @Test
    void visitActivitiesPathNames() {
        TimeSheet timeSheet = TimeSheetHelper.createTimeSheetSimple();
        Visitor<Activity> visitor = new ActivityVisitor();
        timeSheet.accept(visitor, "sample.programming");
    }
}

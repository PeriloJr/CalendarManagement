package com.vacationcalendar.Models;

import com.vacationcalendar.Classes.MonthUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Months {
    private List<Integer> day;
    private List<Holiday> holiday;
    private LocalDate firstDay;
    private LocalDate lastDay;
    private int month;
    private int year;

    public Months(int month, int year) {
        this.month = month +1;
        this.year = year;
        this.setFirstAndLastDay();
    }
    public List<Integer> getDay() {
        return day;
    }
    public void setDay(List<Integer> day) {
        this.day = day;
    }
    public List<Holiday> getHoliday() {
        return holiday;
    }
    public void setHoliday(List<Holiday> holiday) {
        this.holiday = holiday;
    }
    public void setFirstAndLastDay(){
        this.firstDay = LocalDate.of(this.year, this.month, 1);
        this.lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
    }
    public LocalDate getFirstDay() {
        return firstDay;
    }
    public LocalDate getLastDay() {
        return lastDay;
    }
    @Override
    public String toString() {
        MonthUtils mUtils = new MonthUtils(this);
        return  "---------------------\n" +
                mUtils.BuildCalendar();
    }
}

package com.vacationcalendar.Models;

import com.vacationcalendar.Classes.HolidayUtils;
import com.vacationcalendar.Classes.MonthUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Months {
    private List<Integer> day;
    private LocalDate firstDay;
    private LocalDate lastDay;
    private int month;
    private int year;
    private List<Holiday> holidayList;

    public Months(int month, int year, List<Holiday> holidayList) {
        this.month = month +1;
        this.year = year;
        this.setFirstAndLastDay();
        this.holidayList = holidayList;
    }
    public List<Integer> getDay() {
        return day;
    }
    public void setDay(List<Integer> day) {
        this.day = day;
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
        return  "------"+ Month.of(this.month) +"-------\n" +
                mUtils.BuildCalendar(this.holidayList);
    }
}

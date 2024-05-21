package com.vacationcalendar.Classes;

import com.vacationcalendar.Models.Holiday;
import com.vacationcalendar.Models.Months;
import jdk.swing.interop.SwingInterOpUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class MonthUtils {
    private Months currentMonth;
    private LocalDate _currentDate;
    public MonthUtils(Months currentMonth) {
        this.currentMonth = currentMonth;
    }
    public MonthUtils() {
    }
    private String BuildCalendarHeader(List<String> dayInitialLetter){
        StringBuilder calendarHeader = new StringBuilder();
        for(String initial : dayInitialLetter){
            calendarHeader.append(initial + "|");
        }
        return calendarHeader.toString();
    }
    private String BuildFirstWeek(List<Holiday> holidayList){
        StringBuilder firstWeek = new StringBuilder();

        if(currentMonth.getFirstDay().getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            LocalDate iterableDate = currentMonth.getFirstDay();
            do{
                if(IsHoliday(holidayList, iterableDate)){
                    firstWeek.append("XX|");
                    iterableDate = iterableDate.plusDays(1);
                    continue;
                }
                firstWeek.append("0" + iterableDate.getDayOfMonth() + "|");
                iterableDate = iterableDate.plusDays(1);
            }while(!iterableDate.getDayOfWeek().equals(DayOfWeek.SUNDAY));
            return firstWeek.toString();
        }

        for(DayOfWeek day = DayOfWeek.SUNDAY; !day.equals(currentMonth.getFirstDay().getDayOfWeek()); day = day.plus(1)){
            firstWeek.append("--|");
        }

        LocalDate iteratedDate = currentMonth.getFirstDay();
        for(iteratedDate.getDayOfWeek(); !iteratedDate.getDayOfWeek().equals(DayOfWeek.SUNDAY); iteratedDate = iteratedDate.plusDays(1)){
            if(IsHoliday(holidayList, iteratedDate)){
                firstWeek.append("XX|");
                continue;
            }
            firstWeek.append("0" + iteratedDate.getDayOfMonth() + "|");
        }
        _currentDate = NextSunday();
        return firstWeek.toString();
    }
    private LocalDate NextSunday(){
        return currentMonth.getFirstDay().with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
    }
    private String BuildNextWeeks(List<Holiday> holidayList){
        StringBuilder currentWeek = new StringBuilder();
        LocalDate nextWeek = NextSunday();
        for(LocalDate iterableDate = nextWeek; !iterableDate.isAfter(currentMonth.getLastDay()); iterableDate = iterableDate.plusDays(1)){
            String appendValue = "";
            if(IsHoliday(holidayList, iterableDate)){
                appendValue = iterableDate.getDayOfWeek().equals(DayOfWeek.SATURDAY) ? "XX|\n" : "XX|";
                currentWeek.append(appendValue);
            }
            else if(iterableDate.getDayOfWeek().equals(DayOfWeek.SATURDAY)){
                appendValue = iterableDate.getDayOfMonth() < 10 ? "0" + iterableDate.getDayOfMonth() + "|\n" : iterableDate.getDayOfMonth() + "|\n";
                currentWeek.append(appendValue);
            }else{
                appendValue = iterableDate.getDayOfMonth() < 10 ? "0" + iterableDate.getDayOfMonth() + "|" : iterableDate.getDayOfMonth() + "|";
                currentWeek.append(appendValue);
            }
            _currentDate = iterableDate.plusDays(1);
        }

        return currentWeek.toString();
    }
    private boolean IsHoliday(List<Holiday> holidayList, LocalDate currentDate){
        return holidayList.stream()
                .anyMatch(holiday -> currentDate.equals(LocalDate.parse(holiday.getDate())));
    }
    private List<Holiday> GetHolidayList(){
        HolidayUtils hUtils = new HolidayUtils(true, true, true);

        return hUtils.GetAllHolidayList(LocalDateTime.now(), "SP");
    }
    public String BuildCalendar(List<Holiday> holidayList){
        ArrayList<String> dayInitials = new ArrayList<>(List.of("DD", "SS", "TT", "QQ", "QQ", "SS", "SS"));
        String calendarHeader = BuildCalendarHeader(dayInitials);
        String firstWeek = BuildFirstWeek(holidayList);
        String anotherWeeks = BuildNextWeeks(holidayList);
        return calendarHeader + "\n" + firstWeek + "\n" + anotherWeeks.toString();
    }
    public List<LocalDate> DayCalendarList(){
        LocalDate iterableDay = currentMonth.getFirstDay();
        List<LocalDate> MonthDayList = new ArrayList<>();
        while(!iterableDay.equals(currentMonth.getLastDay())){
            MonthDayList.add(iterableDay);
            iterableDay.plusDays(1);
        }
        return MonthDayList;
    }
}

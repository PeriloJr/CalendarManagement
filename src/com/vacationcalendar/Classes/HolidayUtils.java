package com.vacationcalendar.Classes;

import com.vacationcalendar.Models.Holiday;
import com.vacationcalendar.Models.HolidayJson;

import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HolidayUtils {
    static String FILENAME = "HolidayList.txt";
    static String NATIONAL = "nacional";
    static String STATE = "estadual";
    static String HOLIDAY = "feriado";
    static String FACULTATIVE = "facultativo";
    static String CARNIVAL = "Carnaval";
    static String EVE = "Véspera";
    static String WEDNESDAYASH = "Quarta-feira de Cinzas";
    private boolean acceptStateHoliday;
    private boolean acceptFacultativePoint;
    private boolean acceptCarnival;
    public HolidayUtils(boolean acceptStateHoliday, boolean acceptFacultativePoint, boolean acceptCarnival) {
        this.acceptStateHoliday = acceptStateHoliday;
        this.acceptFacultativePoint = acceptFacultativePoint;
        this.acceptCarnival = acceptCarnival;
    }
    public void GetHolidayList(LocalDateTime date, String state){
        HolidayAPI holidayAPI = new HolidayAPI();
        List<Holiday> holidayList = holidayAPI.HolydayList(date, state);
        SaveInTxtFile(holidayList);
        SearchForBridgeDays(holidayList);
    }
    private List<String> GetListOfDays(List<Holiday> holidayList){
        return holidayList.stream().map(Holiday::getDate).toList();
    }
    private LocalDate DataConverter(Holiday holiday){
        String date = holiday.getDate();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                .withZone(ZoneId.systemDefault());;

        return LocalDate.parse(date, dateTimeFormatter);
    }
    private boolean IsCarnivalOrAshWednesday(Holiday holiday){
        return holiday.getName().equals(CARNIVAL) || holiday.getName().equals(WEDNESDAYASH);
    }
    private boolean HolidayExtraConditions(Holiday holiday, boolean acceptStateHoliday, boolean acceptFacultativePoint){
        return ((holiday.getLevel().equals(NATIONAL) || (holiday.getLevel().equals(STATE) && acceptStateHoliday))
                && (holiday.getType().equals(HOLIDAY) || (holiday.getType().equals(FACULTATIVE) && acceptFacultativePoint))
                && !IsCarnivalOrAshWednesday(holiday));
    }
    private boolean IsThursday(LocalDate date){
        return date.getDayOfWeek() == DayOfWeek.THURSDAY;
    }
    private boolean IsTuesday(LocalDate date){
        return date.getDayOfWeek() == DayOfWeek.TUESDAY;
    }
    private boolean RemoveEveDays(Holiday holiday){
        return holiday.getName().contains(EVE);
    }
    private LocalDate BrideableDay(Holiday holiday){
        LocalDate date = DataConverter(holiday);

        if(RemoveEveDays(holiday)){
            return null;
        }

        boolean acceptExtraConditions = HolidayExtraConditions(holiday, this.acceptStateHoliday, this.acceptFacultativePoint);

        if(IsThursday(date) && acceptExtraConditions){
            return date.plusDays(1);
        }else if(IsTuesday(date) && acceptExtraConditions){
            return date.minusDays(1);
        }

        if(IsCarnivalOrAshWednesday(holiday) && this.acceptCarnival){
            return date;
        }

        return null;
    }
    public List<Holiday> SearchForBridgeDays(List<Holiday> holidayList){
        List<Holiday> bridgeHolidays = new ArrayList<>();
        LocalDate brigeableDay = null;
        for(Holiday holiday : holidayList){
            brigeableDay = BrideableDay(holiday);
            if(brigeableDay != null){
                HolidayJson bridgeDayJson = new HolidayJson(brigeableDay.toString(), "bridge", "bridge", "bridge");
                Holiday bridgeDay = new Holiday(bridgeDayJson);
                bridgeHolidays.add(bridgeDay);
            }
        }

        for(Holiday hol : bridgeHolidays){
            System.out.println(hol.getDate());
        }

        return bridgeHolidays;
    }
    private void SaveInTxtFile(List<Holiday> holidayList){
        List<String> holidayListDate = GetListOfDays(holidayList);

        FileWriter file = null;
        try {
            file = new FileWriter(FILENAME);
            for(String holiday : holidayListDate){
                file.write(holiday + "\n");
            }
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

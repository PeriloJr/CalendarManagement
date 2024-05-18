package com.vacationcalendar.Models;

import java.time.LocalDate;

public class Holiday {
    private LocalDate date;
    private String name;
    private String type;
    private String level;

    public String getDate() {
        return date.toString();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }

    public Holiday(HolidayJson holidayJson) {
        this.date = LocalDate.parse(holidayJson.date());
        this.name = holidayJson.name();
        this.type = holidayJson.type();
        this.level = holidayJson.level();
    }
}

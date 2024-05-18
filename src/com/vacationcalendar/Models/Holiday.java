package com.vacationcalendar.Models;

public class Holiday {
    private String date;
    private String name;
    private String type;
    private String level;

    public String getDate() {
        return date;
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
        this.date = holidayJson.date();
        this.name = holidayJson.name();
        this.type = holidayJson.type();
        this.level = holidayJson.level();
    }
}

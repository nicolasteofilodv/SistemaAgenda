package com.autoagenda.app.dto.calendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarForm {

    private List<CalendarDayForm> days = new ArrayList<>();

    public List<CalendarDayForm> getDays() {
        return days;
    }

    public void setDays(List<CalendarDayForm> days) {
        this.days = days;
    }
}
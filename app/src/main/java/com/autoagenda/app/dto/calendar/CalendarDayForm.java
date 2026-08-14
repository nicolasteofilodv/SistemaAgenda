package com.autoagenda.app.dto.calendar;

import java.util.ArrayList;
import java.util.List;

public class CalendarDayForm {

    private String weekDay;
    private String label;
    private String opening;
    private String closening;
    private List<String> timeOptions = new ArrayList<>();

    public String getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getClosening() {
        return closening;
    }

    public void setClosening(String closening) {
        this.closening = closening;
    }

    public List<String> getTimeOptions() {
        return timeOptions;
    }

    public void setTimeOptions(List<String> timeOptions) {
        this.timeOptions = timeOptions;
    }
}
package com.autoagenda.app.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.autoagenda.app.dto.calendar.CalendarForm;
import com.autoagenda.app.services.CalendarService;

@Controller
@RequestMapping("/{userId}/agenda")
public class CalendarController {

    private final CalendarService calendarService;

    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @GetMapping("")
    public String index(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("userId", userId);
        model.addAttribute("calendarForm", this.calendarService.getCalendarForm(userId));
        return "calendar/index";
   }

    @PostMapping("")
    public String save(@PathVariable("userId") Long userId, @ModelAttribute("calendarForm") CalendarForm calendarForm) {
        this.calendarService.saveCalendar(userId, calendarForm);
        return "redirect:/" + userId + "/agenda";
    }

    @PostMapping("/reservations")
    public String reserve(
        @PathVariable("userId") Long userId,
        @RequestParam("date") String date,
        @RequestParam("time") String time,
        @RequestParam("timeFim") String timeFim
    ) {
        this.calendarService.createReservation(userId, date, time, timeFim);
        return "redirect:/" + userId + "/agenda?scheduled=1";
    }
}

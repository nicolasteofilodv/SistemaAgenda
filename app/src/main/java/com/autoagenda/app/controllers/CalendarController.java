package com.autoagenda.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("{userId}/agenda")
public class CalendarController {

    @GetMapping("") public String index() {
        return "calendar/index";
   } 
}

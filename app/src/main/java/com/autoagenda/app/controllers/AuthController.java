package com.autoagenda.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.autoagenda.app.dto.user.RegisterRequest;
import com.autoagenda.app.services.UserService;


@Controller
@RequestMapping("/")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() { return "auth/login"; } 

    @GetMapping("/register")
    public String register(Model model) {  
        model.addAttribute("form", new RegisterRequest());
        return "auth/register"; 
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("form") RegisterRequest form, BindingResult result, Model model) {
        if (result.hasErrors()){ 
            return "auth/register";
        }
        var user = this.userService.createUser(form);
        System.out.println(user);
        return "redirect:/login";
    }

    
}

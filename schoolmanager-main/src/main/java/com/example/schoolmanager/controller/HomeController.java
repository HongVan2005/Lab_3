package com.example.schoolmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home() {
        return "student";
    }
    
    @GetMapping("/student")
    public String student() {
        return "student";
    }
}

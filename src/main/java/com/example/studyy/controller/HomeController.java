package com.example.studyy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {


    @GetMapping("/home")
    public String showHomePage() {
        return "home"; // Isso se refere ao template home.html
    }

    @GetMapping("/verify")
    public String showVerifyPage() {
        return "verify";
    }

    @GetMapping("/sucessPassword")
    public String showSucessPage() {
        return "sucessPassword"; // Isso se refere ao template home.html
    }

    @GetMapping("/errorPassword")
    public String showErrorPage() {
        return "errorPassword"; // Isso se refere ao template home.html
    }
}
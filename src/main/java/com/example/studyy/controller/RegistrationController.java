package com.example.studyy.controller;

import com.example.studyy.entity.User;
import com.example.studyy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register"; // Nome do template HTML para registro
    }

    @PostMapping("/register")
    public String registerUser(User user, Model model) {
        try {
            // Envia os dados para o serviço para registrar o usuário
            userService.registerUser(user.getUsername(), user.getPassword(), user.getEmail());
           return "redirect:/login"; // Redireciona para o login após o registro
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register"; // Retorna à página de registro com erro
        }
    }
}

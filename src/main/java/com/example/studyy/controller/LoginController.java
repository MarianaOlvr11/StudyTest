package com.example.studyy.controller;


import com.example.studyy.entity.User;
import com.example.studyy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    // Exibe a página de login
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    // Processa o login
    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        Optional<User> user = userService.loginUser(username, password);
        if (user.isPresent()) {
            // Se o usuário for autenticado, redireciona para a página principal
            return "redirect:/home"; // ou outra página que você tenha configurado
        } else {
            // Se o login falhar, exibe uma mensagem de erro
            model.addAttribute("error", "Usuário ou senha inválidos");
            return "login"; // Retorna para a página de login
        }
    }
}

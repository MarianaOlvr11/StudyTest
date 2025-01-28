package com.example.studyy.controller;


import com.example.studyy.entity.PasswordResetToken;
import com.example.studyy.entity.User;
import com.example.studyy.repository.PasswordResetTokenRepository;
import com.example.studyy.service.EmailService;
import com.example.studyy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @GetMapping("/forgot")
    public String showForgotPasswordForm() {
        return "forgot"; // nome da página HTML que contém o formulário
    }

    @PostMapping("/forgot")
    public String forgotPassword(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            return "Usuário não encontrado!";
        }

        // Gerar token
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(user);
        resetToken.setToken(token);
        resetToken.setExpirationDate(LocalDateTime.now().plusHours(1));

        // Salvar o token no banco
        passwordResetTokenRepository.save(resetToken);

        // Enviar e-mail com link de redefinição
        String resetLink = "http://localhost:8080/reset?token=" + token;
        emailService.sendEmail(user.getEmail(), "Redefinir senha", "Clique aqui para redefinir sua senha: " + resetLink);

        // Redireciona o usuário para a página de reset
        return "redirect:/verify";
    }


    // Método GET para renderizar a página de redefinição de senha
    @GetMapping("/reset")
    public String showResetForm(@RequestParam String token) {
        // Verificar se o token é válido
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return "Token inválido ou expirado!";
        }
        // Se o token for válido, exibe o formulário de redefinição
        return "reset"; // Retorna a página de reset.html
    }

    @PostMapping("/reset")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return "errorPassword";
        }

        User user = userService.findById(resetToken.getUserId());
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.save(user);

        // Remover token após o uso
        tokenRepository.delete(resetToken);

        return "sucessPassword";
    }



}

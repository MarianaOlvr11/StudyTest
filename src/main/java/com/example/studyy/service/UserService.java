package com.example.studyy.service;


import com.example.studyy.entity.User;
import com.example.studyy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String rawpassword, String email) {
        // Verifica se o usuário já existe
        if (userRepository.findByUsername(username) != null) {
            throw new IllegalArgumentException("Nome de usuário já existe.");
        }

        // Criptografar a senha antes de salvar no banco
        String encodedPassword = passwordEncoder.encode(rawpassword);

        // Criar novo user com a senha criptografada
        User user = new User(username, email);
        user.setPassword(encodedPassword);  // Aqui você precisa garantir que está configurando a senha criptografada

        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String rawpassword) {
        // Buscar pelo userName
        User user = userRepository.findByUsername(username);

        // Verifica se a senha bate
        if (user != null && passwordEncoder.matches(rawpassword, user.getPassword())) {
            return Optional.of(user);
        }

        // Senha não bate, ou usuário não existe
        return Optional.empty();
    }




}

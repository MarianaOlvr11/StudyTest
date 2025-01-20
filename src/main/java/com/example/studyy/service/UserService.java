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


        // criptografar senha
        String encodedPassword = passwordEncoder.encode(rawpassword);

        // Criar novo user
        User user = new User(username, email);
       return userRepository.save(user);


    }

    public Optional<User> loginUser(String username, String rawpassword) {
        // Buscar pelo userName
        User user = userRepository.findByUsername(username);

        // Ve se a senha existe
        if (user != null && passwordEncoder.matches(rawpassword, user.getPasswordHash())) {
            return Optional.of(user);
        }

        // Se nao bater
        return Optional.empty();
    }




}

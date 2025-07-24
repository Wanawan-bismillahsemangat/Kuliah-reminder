package com.dzakwan.kuliahreminder.controller;

import com.dzakwan.kuliahreminder.model.User;
import com.dzakwan.kuliahreminder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String username,
                                  @RequestParam String email,
                                  @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));

        // Tambahkan default role USER
        user.setRole("USER");

        userRepository.save(user);

        return "redirect:/login?registered"; // ke login dengan pesan sukses
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}


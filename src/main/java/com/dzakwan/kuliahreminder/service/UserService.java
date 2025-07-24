package com.dzakwan.kuliahreminder.service;

import com.dzakwan.kuliahreminder.dto.LoginRequest;
import com.dzakwan.kuliahreminder.dto.RegisterRequest;
import com.dzakwan.kuliahreminder.model.User;
import com.dzakwan.kuliahreminder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public String registerUser(RegisterRequest request) {
        if (findByUsername(request.getUsername()) != null) {
            return "Username sudah terdaftar";
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return "Register sukses!";
    }

    public boolean loginUser(LoginRequest request) {
        User user = findByUsername(request.getUsername());
        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return true;
        }
        return false;
    }
}

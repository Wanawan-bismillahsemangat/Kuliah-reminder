package com.dzakwan.kuliahreminder.controller;

import com.dzakwan.kuliahreminder.dto.LoginRequest;
import com.dzakwan.kuliahreminder.dto.RegisterRequest;
import com.dzakwan.kuliahreminder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/web")
public class WebViewController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
public String loginUser(@RequestParam String username,
                        @RequestParam String password,
                        Model model) {

    LoginRequest req = new LoginRequest();
    req.setUsername(username);
    req.setPassword(password);

    boolean result = userService.loginUser(req);

    if (result) {
        return "redirect:/web/dashboard";
    } else {
        model.addAttribute("message", "Username atau password salah");
        return "login";
    }
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {
        RegisterRequest req = new RegisterRequest();
        req.setUsername(username);
        req.setEmail(email);
        req.setPassword(password);

        String result = userService.registerUser(req);
        model.addAttribute("message", result);
        return "register";
    }
}

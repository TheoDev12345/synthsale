package com.doranco.synthsale.controller;

import com.doranco.synthsale.model.User;
import com.doranco.synthsale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {

        return "auth";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password) {
        if (userService.existsByEmail(email)) {
            return "redirect:/login?error";
        }
        User user = new User(username, email, password);
        userService.registerUser(user);
        return "redirect:/";
    }

}

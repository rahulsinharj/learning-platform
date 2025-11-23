package com.antigravity.learningplatform.controller;

import com.antigravity.learningplatform.entity.Role;
import com.antigravity.learningplatform.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, @RequestParam Role role,
            Model model) {
        try {
            userService.registerUser(username, password, role);
            return "redirect:/login?success";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("TEACHER")) {
            return "redirect:/teacher/dashboard";
        } else if (request.isUserInRole("LEARNER")) {
            return "redirect:/learner/dashboard";
        }
        return "redirect:/";
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }
}

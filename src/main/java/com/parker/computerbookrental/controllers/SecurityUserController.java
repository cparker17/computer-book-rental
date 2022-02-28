package com.parker.computerbookrental.controllers;

import com.parker.computerbookrental.exceptions.DuplicateUserException;
import com.parker.computerbookrental.model.security.SecurityUser;
import com.parker.computerbookrental.model.User;
import com.parker.computerbookrental.services.UserService;
import com.parker.computerbookrental.services.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityUserController {
    @Autowired
    UserService userService;

    @Autowired
    SecurityUserService securityUserService;

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @PostMapping("/register")
    public String registerAccount(@ModelAttribute("user") User user, Model model) {
        try {
            userService.registerAccount(user);
            return "register-success";
        } catch (DuplicateUserException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/sign-in")
    public String viewSignInPage(Model model) {
        model.addAttribute("securityUser", new SecurityUser());
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("message", "Invalid username and password.");
        return "login-error";
    }
}

package com.parker.customerwebsite.controllers;

import com.parker.customerwebsite.exceptions.DuplicateCustomerException;
import com.parker.customerwebsite.model.Customer;
import com.parker.customerwebsite.model.security.SecurityUser;
import com.parker.customerwebsite.services.CustomerService;
import com.parker.customerwebsite.services.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityUserController {
    @Autowired
    CustomerService customerService;

    @Autowired
    SecurityUserService securityUserService;

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/register-form")
    public String viewRegisterAccountPage(Model model) {
        model.addAttribute(new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String registerAccount(@ModelAttribute("customer") Customer customer, Model model) {
        try {
            customerService.registerAccount(customer);
            return "register-success";
        } catch (DuplicateCustomerException e) {
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
        model.addAttribute("message", "Username and password incorrect.");
        return "error-page";
    }
}

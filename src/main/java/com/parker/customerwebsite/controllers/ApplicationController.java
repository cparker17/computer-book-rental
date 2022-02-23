package com.parker.customerwebsite.controllers;

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

import java.util.List;

@Controller
public class ApplicationController {
    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return "home";
    }

    @GetMapping("/register")
    public String viewRegisterPage(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }


}

package com.parker.computerbookrental.controllers;

import com.parker.computerbookrental.exceptions.NoSuchUserException;
import com.parker.computerbookrental.model.User;
import com.parker.computerbookrental.model.UserFactory;
import com.parker.computerbookrental.services.BookService;
import com.parker.computerbookrental.services.UserService;
import com.parker.computerbookrental.services.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ApplicationController {
    @Autowired
    SecurityUserService securityUserService;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @GetMapping("/")
    public String viewHomePage() {
        return "home";
    }

    @GetMapping("/register")
    public String viewRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @GetMapping("/dashboard")
    public String viewDashboard(Model model, Authentication auth) throws NoSuchUserException {
        User user = UserFactory.createUser(auth);
        user = userService.getUser(user.getId());
        model.addAttribute("user", user);
        switch (user.getRole().getRole()) {
            case ROLE_USER:
                return "user-dashboard";
            case ROLE_ADMIN:
                return "admin-dashboard";
        }
        model.addAttribute("message", "System error.  Try again.");
        return "error-page";
    }

    @RequestMapping("/books/search")
    public String displaySearchResults(Model model, @RequestParam(value="searchText") String searchText) {
        model.addAttribute("books", bookService.getSearchResults(searchText));
        return "search-results";
    }
}

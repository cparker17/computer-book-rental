package com.parker.computerbookrental.controllers;

import com.parker.computerbookrental.model.User;
import com.parker.computerbookrental.model.UserFactory;
import com.parker.computerbookrental.services.BookService;
import com.parker.computerbookrental.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    private final UserService userService;
    private final BookService bookService;

    public UserController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping("/register-form")
    public String viewRegisterAccountPage(Model model) {
        model.addAttribute(new User());
        return "register";
    }

    @GetMapping("/update")
    public String viewUpdateUserPage(Authentication auth, Model model) {
        model.addAttribute("user", UserFactory.createUser(auth));
        return "update-user";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/dashboard";
    }
}

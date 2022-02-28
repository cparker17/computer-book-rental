package com.parker.computerbookrental.controllers;

import com.parker.computerbookrental.exceptions.NoSuchBookException;
import com.parker.computerbookrental.exceptions.NoSuchUserException;
import com.parker.computerbookrental.model.Book;
import com.parker.computerbookrental.services.BookService;
import com.parker.computerbookrental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @RequestMapping("/update/{id}")
    public String updateUser(@PathVariable(name = "id") Long id, Model model) {
        try {
            model.addAttribute("user", userService.getUser(id));
            return "update-user";
        } catch (NoSuchUserException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }

    }

    @RequestMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id) {
        try {
            userService.deleteUser(id);
            return "redirect:/";
        } catch (NoSuchUserException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/user-list")
    public String viewUserList(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "user-list";
    }

    @GetMapping("/user-books/{id}")
    public String viewUserBooks(@PathVariable(name = "id") Long id, Model model) {
        try {
            model.addAttribute("user", userService.getUser(id));
            return "user-books";
        } catch (NoSuchUserException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @RequestMapping("/books/edit/{id}")
    public String showEditBookPage(Model model, @PathVariable(name = "id") Long id) {
        try {
            Book book = bookService.getBookByBookId(id);
            model.addAttribute("book", book);
            return "edit-book";
        } catch (NoSuchBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/books/rental-history/{id}")
    public String viewBookRentalHistory(Model model, @PathVariable(name = "id") Long id) {
        try {
            model.addAttribute("history", bookService.getBookRentalHistory(id));
            model.addAttribute("book", bookService.getBookByBookId(id));
            return "book-history";
        } catch (NoSuchBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/user/rental-history/{id}")
    public String viewUserRentalHistory(Model model, @PathVariable(name = "id") Long id) {
        try {
            model.addAttribute("history", userService.getUserRentalHistory(id));
            model.addAttribute("user", userService.getUser(id));
            return "user-history";
        } catch(NoSuchUserException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }

    }

}

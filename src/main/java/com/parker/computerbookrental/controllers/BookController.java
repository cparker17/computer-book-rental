package com.parker.computerbookrental.controllers;

import com.parker.computerbookrental.exceptions.DuplicateBookException;
import com.parker.computerbookrental.exceptions.NoSuchBookException;
import com.parker.computerbookrental.model.Book;
import com.parker.computerbookrental.model.UserFactory;
import com.parker.computerbookrental.model.User;
import com.parker.computerbookrental.services.BookService;
import com.parker.computerbookrental.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    @GetMapping
    public String viewBooksList(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book-index";
    }

    @GetMapping("/new-arrivals")
    public String viewNewArrivals(Model model) {
        try {
            model.addAttribute("books", bookService.getNewArrivals());
            return "new-arrivals";
        } catch (NoSuchBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/new")
    public String showNewBookPage(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "new-book";
    }

    @RequestMapping("/save")
    public String saveBook(Model model, @Valid @ModelAttribute("book") Book book, Errors errors) {
        if (errors.hasErrors()) {
            return "new-book";
        }
        try {
            book.setDateAdded(LocalDate.now());
            bookService.saveBook(book);
            return "redirect:/dashboard";
        } catch (DuplicateBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @RequestMapping("/available")
    public String viewAvailableBooks(Model model) {
        try {
            final List<Book> availableBooksList = bookService.getAvailableBooks();
            model.addAttribute("availableBooksList", availableBooksList);
            return "book-index";
        } catch (NoSuchBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @RequestMapping("/update/{id}")
    public String updateBook(@PathVariable(name = "id") Long id,
                             @ModelAttribute(name="book") Book book) {
        bookService.updateBook(book);
        return "redirect:/dashboard";
    }

    @RequestMapping("/delete/{id}")
    public String deleteBook(Model model, @PathVariable(name = "id") Long id) {
        try {
            bookService.deleteBook(id);
            return "redirect:/books";
        } catch (NoSuchBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @RequestMapping("/checkout/{bookId}")
    public String checkoutBook(@PathVariable(name = "bookId") Long bookId,
                               Authentication auth, Model model) {
        User user = UserFactory.createUser(auth);
        Book book = bookService.checkoutBook(bookId, user.getId());
        userService.checkoutBook(book, user);
        model.addAttribute("user", user);
        return "user-dashboard";
    }

    @RequestMapping("/return/{bookId}")
    public String returnBook(@PathVariable(name = "bookId") Long bookId,
                             Authentication auth, Model model) {
        bookService.returnBook(bookId);
        User user = UserFactory.createUser(auth);
        userService.returnBook(bookId, user);
        model.addAttribute("user", user);
        return "redirect:/dashboard";
    }

    @GetMapping("/cover-image/{bookId}")
    public String viewCoverImage(@PathVariable(name = "bookId") Long bookId, Model model) {
        try {
            model.addAttribute("book", bookService.getBookByBookId(bookId));
            return "cover-image";
        } catch (NoSuchBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }
}

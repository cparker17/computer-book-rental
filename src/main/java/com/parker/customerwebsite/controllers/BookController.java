package com.parker.customerwebsite.controllers;

import com.parker.customerwebsite.exceptions.DuplicateBookException;
import com.parker.customerwebsite.exceptions.NoSuchBookException;
import com.parker.customerwebsite.model.Book;
import com.parker.customerwebsite.services.BookService;
import com.parker.customerwebsite.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class BookController {
    private final BookService bookService;
    private final CustomerService customerService;

    public BookController(BookService bookService, CustomerService customerService) {
        this.bookService = bookService;
        this.customerService = customerService;
    }

    @GetMapping("/books")
    public String viewBooksList(Model model) {
        List<Book> bookList = bookService.getAllBooks();
        model.addAttribute("bookList", bookList);
        return "book-index";
    }

    @GetMapping("/books/new")
    public String showNewBookPage(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "new-book";
    }

    @RequestMapping("/books/save")
    public String saveBook(Model model, @ModelAttribute("book") Book book) {
        try {
            bookService.saveBook(book);
            return "redirect:/books";
        } catch (DuplicateBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @RequestMapping("/books/available")
    public String viewAvailableBooks(Model model) {
        try {
            final List<Book> availableBooksList = bookService.getAvailableBooks();
            model.addAttribute("availableBooksList", availableBooksList);
            return "available-books";
        } catch (NoSuchBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @RequestMapping("/books/update/{id}")
    public String updateBook(@PathVariable(name = "id") Long id,
                                 @ModelAttribute("book") Book book, Model model) {
        if (!id.equals(book.getId())) {
            model.addAttribute("message",
                    "Cannot update, book id " + book.getId()
                            + " doesn't match id to be updated: " + id + ".");
            return "error-page";
        }
        bookService.updateBook(book);
        return "redirect:/books";
    }

    @RequestMapping("/books/delete/{id}")
    public String deleteBook(Model model, @PathVariable(name = "id") Long id) {
        try {
            bookService.deleteBook(id);
            return "redirect:/books";
        } catch (NoSuchBookException e) {
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

    @RequestMapping("/books/checkout/{bookId}/{customerId}")
    public String checkoutBook(@PathVariable(name = "bookId") Long bookId,
                               @PathVariable(name = "customerId") Long customerId) {
        Book book = bookService.checkoutBook(bookId, customerId);
        customerService.checkoutBook(book, customerId);
        return "redirect:/";
    }

    @RequestMapping("/books/return/{bookId}/{customerId}")
    public String returnBook(@PathVariable(name = "bookId") Long bookId,
                             @PathVariable(name = "customerId") Long customerId) {
        Book book = bookService.returnBook(bookId);
        customerService.returnBook(book, customerId);
        return "redirect:/";
    }
}

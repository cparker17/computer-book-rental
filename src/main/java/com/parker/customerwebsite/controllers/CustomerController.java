package com.parker.customerwebsite.controllers;

import com.parker.customerwebsite.exceptions.DuplicateCustomerException;
import com.parker.customerwebsite.exceptions.NoSuchBookException;
import com.parker.customerwebsite.exceptions.NoSuchCustomerException;
import com.parker.customerwebsite.model.Book;
import com.parker.customerwebsite.model.Customer;
import com.parker.customerwebsite.services.BookService;
import com.parker.customerwebsite.services.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final BookService bookService;

    public CustomerController(CustomerService customerService, BookService bookService) {
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String vewHomePage(Model model) {
        List<Customer> customerList = customerService.getAllCustomers();
        model.addAttribute("customerList", customerList);
        return "index";
    }

    @GetMapping("/new")
    public String showNewCustomerPage(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "new-customer";
    }

    @PostMapping(value = "/save")
    public String saveCustomer(Model model, @ModelAttribute("customer") Customer customer) {
        try {
            customerService.saveCustomer(customer);
            return "redirect:/";
        } catch (DuplicateCustomerException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditCustomerPage(@PathVariable(name = "id") Long id) {
        try {
            ModelAndView mav = new ModelAndView("edit-customer");
            mav.addObject("customer", customerService.getCustomer(id));
            return mav;
        } catch (NoSuchCustomerException e) {
            ModelAndView mav = new ModelAndView("error-page");
            mav.addObject("NoSuchCustomerMessage", e.getMessage());
            return mav;
        }
    }

    @PostMapping("/update/{id}")
    public String updateCustomer(@PathVariable(name = "id") Long id,
                                 @ModelAttribute("customer") Customer customer, Model model) {
        if (!id.equals(customer.getId())) {
            model.addAttribute("message",
                    "Cannot update, customer id " + customer.getId()
                            + " doesn't match id to be updated: " + id + ".");
            return "error-page";
        }
        customerService.updateCustomer(customer);
        return "redirect:/";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable(name = "id") Long id) {
        try {
            customerService.deleteCustomer(id);
            return "redirect:/";
        } catch (NoSuchCustomerException e) {
            return e.getMessage();
        }
    }

    @RequestMapping("/checkout-book/{id}")
    public String customerBookCheckout(Model model, @PathVariable(name = "id") Long id) {
        try {
            List<Book> availableBooksList = bookService.getAvailableBooks();
            model.addAttribute("availableBooksList", availableBooksList);
            model.addAttribute("customerId", id);
            return "available-books";
        } catch (NoSuchBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }

    @RequestMapping("/return-book/{id}")
    public String customerBookReturn(Model model, @PathVariable(name = "id") Long id) {
        try {
            List<Book> customerBooks = bookService.getCustomerBooks(id);
            model.addAttribute("customerBooks", customerBooks);
            model.addAttribute("customerId", id);
            return "customer-books";
        } catch (NoSuchBookException e) {
            model.addAttribute("message", e.getMessage());
            return "error-page";
        }
    }
}

package com.parker.customerwebsite;

import com.parker.customerwebsite.model.Book;
import com.parker.customerwebsite.model.Customer;
import com.parker.customerwebsite.services.BookService;
import com.parker.customerwebsite.services.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class CustomerWebsiteApplication {
    private final CustomerService customerService;
    private final BookService bookService;

    public CustomerWebsiteApplication(CustomerService customerService, BookService bookService) {
        this.customerService = customerService;
        this.bookService = bookService;
    }

    public static void main(String[] args) {
        SpringApplication.run(CustomerWebsiteApplication.class);

    }

    @Bean
    public CommandLineRunner loadInitialData(CustomerService customerService) {
        return (args) -> {
            if (customerService.getAllCustomers().isEmpty()) {
                customerService.saveAllCustomer(Arrays.asList(
                        Customer.builder().fullName("Customer 1").emailAddress("customer1@gmail.com").address("Customer Address One").age(30).build(),
                        Customer.builder().fullName("Customer 2").emailAddress("customer2@gmail.com").address("Customer Address Two").age(28).build(),
                        Customer.builder().fullName("Customer 3").emailAddress("customer3@gmail.com").address("Customer Address Three").age(32).build()));
            }
            if (bookService.getAllBooks().isEmpty()) {
                bookService.saveAllBooks(Arrays.asList(
                        Book.builder().title("Java Programming").author("Harvey Deitel").isbn("12345.21").build(),
                        Book.builder().title("Effective Java").author("Joshua Bloc").isbn("928377.21").build(),
                        Book.builder().title("Clean Code").author("Robert Martin").isbn("263748.67").build(),
                        Book.builder().title("UML Distilled").author("Martin Fowler").isbn("938364.71").build()));
            }
        };
    }
}

package com.parker.customerwebsite;

import com.parker.customerwebsite.model.Book;
import com.parker.customerwebsite.model.Customer;
import com.parker.customerwebsite.model.CustomerDetails;
import com.parker.customerwebsite.model.Role;
import com.parker.customerwebsite.repositories.CustomerRepository;
import com.parker.customerwebsite.repositories.RoleRepository;
import com.parker.customerwebsite.services.BookService;
import com.parker.customerwebsite.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Set;

import static com.parker.customerwebsite.model.Role.Roles.ROLE_ADMIN;
import static com.parker.customerwebsite.model.Role.Roles.ROLE_USER;

@SpringBootApplication
public class CustomerWebsiteApplication {
    private final CustomerService customerService;
    private final BookService bookService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CustomerRepository customerRepository;

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
            if (roleRepository.findAll().isEmpty()) {
                Role USER = new Role(ROLE_USER);
                Role ADMIN = new Role(ROLE_ADMIN);
                roleRepository.saveAll(Set.of(USER, ADMIN));
            }

//            if (customerRepository.findAll().isEmpty()) {
//                CustomerDetails customerDetails = new CustomerDetails();
//                Customer customer = new Customer();
//                customerDetails.setRole(roleRepository.findById(1L).get());
//                customer.setCustomerDetails(customerDetails);
//                customerRepository.save(customer);
//            }

        };
    }
}

package com.parker.customerwebsite.services;

import com.parker.customerwebsite.exceptions.DuplicateCustomerException;
import com.parker.customerwebsite.exceptions.NoSuchCustomerException;
import com.parker.customerwebsite.model.Book;
import com.parker.customerwebsite.model.Customer;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer saveCustomer(Customer customer) throws DuplicateCustomerException;
    Customer getCustomer(Long id) throws NoSuchCustomerException;
    void deleteCustomer(Long id) throws NoSuchCustomerException;
    List<Customer> saveAllCustomer(List<Customer> customerList);
    void returnBook(Book book, Long customerId);
    void checkoutBook(Book book, Long customerId);
    Customer updateCustomer(Customer customer);
}

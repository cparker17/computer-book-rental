package com.parker.customerwebsite.services.impl;

import com.parker.customerwebsite.exceptions.DuplicateCustomerException;
import com.parker.customerwebsite.exceptions.NoSuchCustomerException;
import com.parker.customerwebsite.model.Book;
import com.parker.customerwebsite.model.Customer;
import com.parker.customerwebsite.repositories.CustomerRepository;
import com.parker.customerwebsite.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    final CustomerRepository customerRepository;

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) throws DuplicateCustomerException {
        if (customerRepository.findDistinctByEmailAddress(customer.getEmailAddress()) != null) {
            throw new DuplicateCustomerException("This customer already exists in the system.");
        } else {
            return customerRepository.save(customer);
        }
    }

    @Override
    public Customer getCustomer(Long id) throws NoSuchCustomerException {
        Optional optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) {
            throw new NoSuchCustomerException("A customer with that id does not exist.");
        } else {
            return (Customer) optionalCustomer.get();
        }
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) throws NoSuchCustomerException {
        if (customerRepository.findById(id).isEmpty()) {
            throw new NoSuchCustomerException("A customer with that id does not exist.");
        } else {
            customerRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public List<Customer> saveAllCustomer(List<Customer> customerList) {
        return customerRepository.saveAll(customerList);
    }

    @Override
    public void returnBook(Book book, Long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        customer.getBooks().remove(book);
        customerRepository.save(customer);
    }

    @Override
    public void checkoutBook(Book book, Long customerId) {
        Customer customer = customerRepository.findById(customerId).get();
        customer.addBook(book);
        customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}

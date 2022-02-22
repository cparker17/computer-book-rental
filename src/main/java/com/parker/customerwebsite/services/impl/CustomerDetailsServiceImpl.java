package com.parker.customerwebsite.services.impl;

import com.parker.customerwebsite.model.Customer;
import com.parker.customerwebsite.repositories.CustomerRepository;
import com.parker.customerwebsite.services.CustomerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerDetailsServiceImpl implements CustomerDetailsService {
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByCustomerDetails_Username(username).getCustomerDetails();
    }
}

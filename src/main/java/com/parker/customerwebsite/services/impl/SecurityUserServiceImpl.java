package com.parker.customerwebsite.services.impl;

import com.parker.customerwebsite.model.security.SecurityUser;
import com.parker.customerwebsite.repositories.UserRepository;
import com.parker.customerwebsite.services.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new SecurityUser(userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("Username not found.")));
    }
}

package com.parker.computerbookrental.model;

import com.parker.computerbookrental.model.User;
import com.parker.computerbookrental.model.security.SecurityUser;
import org.springframework.security.core.Authentication;

public class UserFactory {
    public static User createUser(Authentication authentication) {
        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        return securityUser.getUser();
    }
}

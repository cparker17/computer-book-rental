package com.parker.customerwebsite.repositories;

import com.parker.customerwebsite.model.security.SecurityUser;
import com.parker.customerwebsite.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}

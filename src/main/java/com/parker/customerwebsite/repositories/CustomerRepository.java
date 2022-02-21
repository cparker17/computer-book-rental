package com.parker.customerwebsite.repositories;

import com.parker.customerwebsite.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findDistinctByEmailAddress(String email);
    Customer findByCustomerDetails_Username(String username);
}

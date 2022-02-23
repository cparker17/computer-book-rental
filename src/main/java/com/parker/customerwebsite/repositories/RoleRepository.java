package com.parker.customerwebsite.repositories;

import com.parker.customerwebsite.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleById(Long id);
}

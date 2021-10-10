package com.sporty_shoes.repository;

import com.sporty_shoes.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role,Integer> {
    
}

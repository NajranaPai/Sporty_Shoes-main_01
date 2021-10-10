package com.sporty_shoes.repository;

import com.sporty_shoes.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    
    

}

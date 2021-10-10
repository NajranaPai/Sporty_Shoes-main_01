package com.sporty_shoes.repository;

import com.sporty_shoes.model.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>{
    public List<Product> findAllByCategory_Id(int id);
}

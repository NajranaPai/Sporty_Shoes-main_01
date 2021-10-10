package com.sporty_shoes.service;

import java.util.List;
import java.util.Optional;


import com.sporty_shoes.model.Product;
import com.sporty_shoes.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    public List<Product> getAlProducts(){
        return this.productRepository.findAll();
    }

    public void addProduct(Product product){
        this.productRepository.save(product);
    }

    public void deleteProduct(long id){
        this.productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(long id){
        return this.productRepository.findById(id);
    }

    public List<Product> getProductByCategoryId(int id){
        return this.productRepository.findAllByCategory_Id(id);
    }

}

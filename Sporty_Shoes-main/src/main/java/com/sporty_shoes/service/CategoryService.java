package com.sporty_shoes.service;

import com.sporty_shoes.model.Category;
import com.sporty_shoes.repository.CategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    public void addCategory(Category category){
        this.categoryRepository.save(category);
    }

    public List<Category> allCategories(){
        return this.categoryRepository.findAll();
    }

    public void deleteCategory(int id){
        this.categoryRepository.deleteById(id);
    }

    public Optional<Category> getCategoryById(int id){
       return this.categoryRepository.findById(id);
    }

}

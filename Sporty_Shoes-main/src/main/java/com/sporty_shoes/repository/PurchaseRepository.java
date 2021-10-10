package com.sporty_shoes.repository;

import java.util.Date;
import java.util.List;

import com.sporty_shoes.model.Purchase;

import org.springframework.data.jpa.repository.JpaRepository;



public interface PurchaseRepository extends JpaRepository<Purchase,Long>{
    public List<Purchase> findAllByDate(Date date);
  
    public List<Purchase> findAllPurchaseByProductCategoryId(int id);

    public List<Purchase> findByDateAndProductCategoryId(Date date,int id);
}

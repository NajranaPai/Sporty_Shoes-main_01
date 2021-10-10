package com.sporty_shoes.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sporty_shoes.model.Purchase;
import com.sporty_shoes.repository.PurchaseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {
    
    @Autowired
    private PurchaseRepository purchaseRepository;

    public void addPurchase(Purchase purchase){
        this.purchaseRepository.save(purchase);
    }

    public List<Purchase> getPurchasesByCategory(int id){
        return this.purchaseRepository.findAllPurchaseByProductCategoryId(id);
    }

    public List<Purchase> getPurchasesByDate(Date date){
        return this.purchaseRepository.findAllByDate(date);
    }

    public List<Purchase> getAllPurchases(){
        return this.purchaseRepository.findAll();
    }

    public List<Purchase> getPurchaseByDateAndCategoryId(Date date, int id){
        List<Purchase> purchases = this.purchaseRepository.findAllByDate(date);
        List<Purchase> filteredPurchases = new ArrayList<>();
        purchases.stream().filter(purchase-> id == purchase.getProduct().getCategory().getId()).forEach(purchase->filteredPurchases.add(purchase));
        return filteredPurchases;
    }
    


}

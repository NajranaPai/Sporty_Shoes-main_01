package com.sporty_shoes.controller;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sporty_shoes.global.GlobalData;
import com.sporty_shoes.model.Purchase;
import com.sporty_shoes.model.User;
import com.sporty_shoes.repository.UserRepository;
import com.sporty_shoes.service.CategoryService;
import com.sporty_shoes.service.PurchaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class PurchaseController{

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/purchased")
    public String purchases(Authentication authentication, Model model){
    String email = authentication.getName();
    User user = this.userRepository.findUserByEmail(email).get();
    Date date = new Date();

    GlobalData.cart.forEach(product->{
      Purchase purchase =  new Purchase();
      purchase.setDate(date);
      purchase.setProduct(product);
      purchase.setUser(user);
      this.purchaseService.addPurchase(purchase);
    });

    model.addAttribute("purchased", true);
    GlobalData.cart.clear();
    return "cart";
    }

    @GetMapping("/admin/purchaseReports")
    public String getReports(Model model){
      model.addAttribute("categories", this.categoryService.allCategories());
      model.addAttribute("purchases", this.purchaseService.getAllPurchases());
      return "purchase-report";
    }

     @PostMapping("/admin/purchaseReports")
    public String postReports(Model model, @RequestParam("category") int cId, @RequestParam("date") String date) throws ParseException{
       model.addAttribute("categories", this.categoryService.allCategories());
      if(cId == 0 && date.length()==0){
       model.addAttribute("purchases", this.purchaseService.getAllPurchases());
      }
      if(cId == 0 && date.length()!=0){
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        model.addAttribute("purchases", this.purchaseService.getPurchasesByDate(d));
      }
      if(cId != 0 && date.length()==0){
        model.addAttribute("purchases", this.purchaseService.getPurchasesByCategory(cId));
      }
      if(cId != 0 && date.length()!=0){
        Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        model.addAttribute("purchases", this.purchaseService.getPurchaseByDateAndCategoryId(d,cId));
      }
      return "purchase-report";
    }
}
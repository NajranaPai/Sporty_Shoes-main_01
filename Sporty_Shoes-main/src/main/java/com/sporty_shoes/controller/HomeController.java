
package com.sporty_shoes.controller;

import com.sporty_shoes.global.GlobalData;
import com.sporty_shoes.service.CategoryService;
import com.sporty_shoes.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class HomeController {
    
     @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;
    
    @GetMapping({"/","/home"})
    public String home(Model model){
         model.addAttribute("cartCount", GlobalData.cart.size());
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model){
         model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", this.categoryService.allCategories());
        model.addAttribute("products", this.productService.getAlProducts());
        return "shop";
    }

    @GetMapping(value="/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable("id") int id){
         model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("categories", this.categoryService.allCategories());
        model.addAttribute("products", this.productService.getProductByCategoryId(id));
        return "shop";
    }
    
    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable("id") long id){
         model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("product", this.productService.getProductById(id).get());
        return "viewProduct";
    }


}

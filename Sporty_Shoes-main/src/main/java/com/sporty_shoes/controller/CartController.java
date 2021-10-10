package com.sporty_shoes.controller;

import com.sporty_shoes.global.GlobalData;
import com.sporty_shoes.model.Product;
import com.sporty_shoes.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CartController {
    
    @Autowired
    private ProductService productService;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable("id") Long id){
        GlobalData.cart.add(this.productService.getProductById(id).get());  
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String getCart(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String removeItem(@PathVariable("index") int index){
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model){
      model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
      return"checkout";
    }

}

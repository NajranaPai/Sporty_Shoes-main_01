package com.sporty_shoes.global;

import java.util.ArrayList;
import java.util.List;

import com.sporty_shoes.model.Product;

public  class GlobalData {
    public static List<Product> cart;
    static{
        cart = new ArrayList<>();
    }

}

package com.shop.pojo;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    public List<ProductInfo> cartItems = new ArrayList<>();

    public void addToCart(ProductInfo product) {
        cartItems.add(product);
    }

    public List<ProductInfo> getCartItems() {
        return cartItems;
    }
}


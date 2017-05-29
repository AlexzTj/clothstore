package com.ecommerce.dao;


import com.ecommerce.model.Cart;

public interface CartDao {
    Cart create();
    Cart read();
    void update(Cart cart);
    void remove(Cart cart);
}

package com.ecommerce.model;


import com.ecommerce.model.dto.ProductDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private String cartId;
    private Map<Integer, CartItem> cartItems = new HashMap<>();
    private double grandTotal;

    public Cart() {
    }


    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Map<Integer, CartItem> getCartItems() {
        return new HashMap<>(cartItems);
    }

    public void setCartItems(Map<Integer, CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getGrandTotal() {
        updateGrandTotal();
        return grandTotal;
    }

    public void addCartItem(CartItem item) {
        Integer productId = item.getProduct().getId();
        if (cartItems.containsKey(productId)) {
            CartItem existing = cartItems.get(productId);
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
            cartItems.put(productId, existing);
        }else{
            cartItems.put(productId, item);
        }
        updateGrandTotal();
    }

    public void removeCartItem(CartItem item){
        cartItems.remove(item.getProduct().getId());
        updateGrandTotal();
    }

    private void updateGrandTotal() {
        grandTotal = cartItems.values().stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public void updateQuantity(List<ProductDTO> payload) {
        payload.forEach(item ->
        {
            if (cartItems.containsKey(item.getProductId())) {
                CartItem existing = cartItems.get(item.getProductId());
                existing.setQuantity(item.getQuantity());
                cartItems.put(item.getProductId(), existing);
            }
        });
    }
}

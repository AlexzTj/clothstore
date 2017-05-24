package com.ecommerce.model;


import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CartItem {
    private Product product;
    private int quantity;
    private double totalPrice;

    private CartItem() {
    }

    public CartItem(Product product) {
        if (product == null) throw new IllegalArgumentException("product can not be null");
        this.product = product;
        quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("product can not be null");
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("cartitem quantity can not be less than 1");
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartItem cartItem = (CartItem) o;
        return getProduct().equals(cartItem.getProduct());
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(product)
                .hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

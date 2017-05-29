package com.ecommerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
public class CartItem {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JsonIgnore
    private Order order;
    @Transient
    private Product product;
    @JsonIgnore
    private String title;

    @JsonIgnore
    private Integer productId;
    private int quantity;
    private double totalPrice;

    private CartItem() {
    }

    public CartItem(Product product) {
        if (product == null) throw new IllegalArgumentException("product can not be null");
        this.product = product;
        this.title = product.getTitle();
        this.productId = product.getId();
        quantity = 1;
        updateTotalPrice();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (product == null) throw new IllegalArgumentException("product can not be null");
        this.product = product;
        this.title = product.getTitle();
        this.productId = product.getId();
        updateTotalPrice();
    }
    public void updateTotalPrice(){
        BigDecimal bd = new BigDecimal(product.getPrice() * quantity);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        this.totalPrice = bd.doubleValue();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("cartitem quantity can not be less than 1");
        this.quantity = quantity;
        updateTotalPrice();
    }

    public double getTotalPrice() {
      return totalPrice;
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

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

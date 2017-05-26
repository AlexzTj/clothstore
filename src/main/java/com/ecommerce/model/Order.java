package com.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class Order implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private User user;
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @NotEmpty
    private List<CartItem> cartItems;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;
    @Enumerated(EnumType.STRING)
    private DeliveryMethod delivery = DeliveryMethod.FEDEX;
    private String payment;
    @CreationTimestamp
    @Column(updatable = false)
    @JsonIgnore
    private LocalDateTime createdOn;
    @Transient
    @JsonIgnore
    private Cart cart;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public DeliveryMethod getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryMethod delivery) {
        this.delivery = delivery;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return new EqualsBuilder()
                .append(getCreatedOn(),order.getCreatedOn())
                .append(getUser(),order.getUser())
                .append(getCartItems(),order.getCartItems())
                .append(getDelivery(),order.getDelivery())
                .append(getStatus(),order.getStatus())
                .append(getPayment(),order.getPayment())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getCreatedOn())
                .append(getUser())
                .append(getCartItems())
                .append(getDelivery())
                .append(getStatus())
                .append(getPayment())
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}

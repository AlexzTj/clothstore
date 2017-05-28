package com.ecommerce.service;


import com.ecommerce.model.Order;
import com.ecommerce.model.constants.OrderStatus;

import java.util.List;

public interface OrderService {
    Order getOrderById(Integer id);
    List<Order> getOrderByUser(String userId);
    void addOrder(Order order);
    void updateOrderStatus(Integer orderId, OrderStatus orderStatus);
    void deleteOrder(Integer id);

    List<Order> getAllOrders();
}

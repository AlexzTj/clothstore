package com.ecommerce.service.impl;

import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Order getOrderById(Integer id) {
        return sessionFactory.getCurrentSession().get(Order.class, id);
    }

    @Override
    public List<Order> getOrderByUser(String userId) {
        return sessionFactory.getCurrentSession().createQuery("from Order o where o.user.id =:id").setParameter("id", userId).getResultList();
    }

    @Override
    public void addOrder(Order order) {
        sessionFactory.getCurrentSession().saveOrUpdate(order);
    }

    @Override
    public void updateOrder(Order order) {
        sessionFactory.getCurrentSession().update(order);
    }

    @Override
    public void deleteOrder(Integer id) {
        sessionFactory.getCurrentSession().delete(getOrderById(id));
    }
}

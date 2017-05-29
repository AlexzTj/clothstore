package com.ecommerce.dao.impl;

import com.ecommerce.dao.UserDao;
import com.ecommerce.model.User;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUserById(String id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public void registerUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
    }

    @Override
    public void deleteUser(String id) {
        sessionFactory.getCurrentSession().delete(getUserById(id));
    }

    @Override
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }
}

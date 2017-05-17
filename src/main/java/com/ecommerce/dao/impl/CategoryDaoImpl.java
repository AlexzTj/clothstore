package com.ecommerce.dao.impl;

import com.ecommerce.dao.CategoryDao;
import com.ecommerce.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.hibernate.query.Query;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository

public class CategoryDaoImpl implements CategoryDao, ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public List<Category> getAllCategories() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Category");
        List<Category> results = query.list();
        session.flush();
        return results;
    }

    @Override
    @Transactional
    public void addCategory(Category category){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(category);
        session.flush();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        Category category = new Category();
//        category.setName("products");
//        category.setDescription("some description");
//        addCategory(category);
    }
}

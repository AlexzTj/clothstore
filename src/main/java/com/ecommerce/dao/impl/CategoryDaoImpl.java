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
import javax.persistence.NoResultException;
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
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Session session = sessionFactory.getCurrentSession();
        try {
            Query query = session.createQuery("from Category c where c.name='default'");
            Category c = (Category) query.getSingleResult();
        }catch (NoResultException e){
            Category category = new Category();
            category.setName("default");
            category.setDescription("some description");
            session.saveOrUpdate(category);
        }
    }
}

package com.ecommerce.dao.impl;

import com.ecommerce.dao.CategoryDao;
import com.ecommerce.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao, ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> getAllCategories() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Category");
        List<Category> results = query.list();
        return results;
    }

    @Override
    public void addCategory(Category category){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(category);
    }

    @Override
    public Category getCategoryById(Integer id) {
        return sessionFactory.getCurrentSession().get(Category.class,id);
    }

    @Override
    public void updateCategory(Category category) {
         sessionFactory.getCurrentSession().update(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(getCategoryById(id));
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

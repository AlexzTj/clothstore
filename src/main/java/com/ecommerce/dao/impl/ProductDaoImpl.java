package com.ecommerce.dao.impl;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoImpl.class);
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(product);
    }

    @Override
    public void updateProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.update(product);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(getProductById(productId, false));
    }

    @Override
    public Product getProductById(Integer id, boolean fetchAll) {
        Session session = sessionFactory.getCurrentSession();
        Product product = null;
        if (fetchAll) {
            try {
                product = (Product) session.createQuery("from Product p join fetch p.category left join fetch p.imageMetaSet where p.id = :id")
                        .setParameter("id", id)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOGGER.error("no product with id {}", id, e);
            }
        } else {
            product = session.get(Product.class, id);
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Product");
        List<Product> all = query.list();
        return all;
    }

    @Override
    public List<Product> fetchHotProducts(int limit) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Product as p   " +
                "left join fetch p.imageMetaSet image where image.type = 'FEATURED' order by p.timestamp desc").setMaxResults(limit).getResultList();
    }

    @Override
    public List<Product> getProductsByCategoryId(Integer categoryId) {
        return sessionFactory.getCurrentSession().createQuery("from Product p left join fetch p.imageMetaSet image where image.type = 'FEATURED' and p.category.id = :id")
                .setParameter("id", categoryId)
                .getResultList();
    }

    @Override
    public Long countProductsByCategoryId(Integer categoryId) {
        return (Long) sessionFactory.getCurrentSession().createQuery("select count(distinct p.id) from Product p where p.category.id=:id")
                .setParameter("id", categoryId).getSingleResult();
    }
}

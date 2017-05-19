package com.ecommerce.dao.impl;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {
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
        session.delete(getProductById(productId,false));
    }

    @Override
    public Product getProductById(Integer id, boolean fetchAll) {
        Session session = sessionFactory.getCurrentSession();
        Product product;
        if(fetchAll){
           product = (Product) session.createQuery("from Product p join fetch p.category left join fetch p.imageMetaSet where p.id = :id")
                   .setParameter("id",id)
                   .getSingleResult();
        }else {
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
    public List<Product> fetchProductsWithFeaturedImage(int limit) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Product as p   " +
                "left join fetch p.imageMetaSet image where image.type = 'FEATURED' order by p.timestamp desc").setMaxResults(limit).getResultList();
    }
}

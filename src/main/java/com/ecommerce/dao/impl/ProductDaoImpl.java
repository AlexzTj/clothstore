package com.ecommerce.dao.impl;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Image;
import com.ecommerce.model.ImageType;
import com.ecommerce.model.Product;
import com.ecommerce.service.StorageService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(product);
    }

    @Override
    public void deleteProduct(Integer productId) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(getProductById(productId));
    }

    @Override
    public Product getProductById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        Product product = session.get(Product.class, id);
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

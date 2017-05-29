package com.ecommerce.dao.impl;

import com.ecommerce.dao.ImageDao;
import com.ecommerce.model.Image;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class ImageDaoImpl implements ImageDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void removeImageById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(getImageById(id));
    }

    @Override
    public Image getImageById(Integer id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Image.class, id);
    }

}

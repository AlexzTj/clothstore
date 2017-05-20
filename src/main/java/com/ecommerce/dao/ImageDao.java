package com.ecommerce.dao;

import com.ecommerce.model.Image;

public interface ImageDao {
    void removeImageById(Integer id);
    Image getImageById(Integer id);
}

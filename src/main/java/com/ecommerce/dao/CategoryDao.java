package com.ecommerce.dao;

import com.ecommerce.model.Category;

import java.util.List;

/**
 * Created by atujicov on 5/12/2017.
 */
public interface CategoryDao {
    List<Category> getAllCategories();

    void addCategory(Category category);
}

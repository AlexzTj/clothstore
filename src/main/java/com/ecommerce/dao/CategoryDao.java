package com.ecommerce.dao;

import com.ecommerce.model.Category;

import java.util.List;

public interface CategoryDao {
    List<Category> getAllCategories();

    void addCategory(Category category);

    Category getCategoryById(Integer id);

    void updateCategory(Category category);

    void deleteCategory(Integer id);

    Category getCategoryBySlug(String categorySlug);
}

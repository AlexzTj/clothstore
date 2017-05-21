package com.ecommerce.service;


import com.ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    void addCategory(Category category);

    Category getCategoryById(Integer id);

    void updateCategory(Category category);

    void deleteCategory(Integer id);
}

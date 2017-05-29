package com.ecommerce.service.impl;


import com.ecommerce.dao.CategoryDao;
import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    @Override
    public void addCategory(Category category) {
         categoryDao.addCategory(category);
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryDao.getCategoryById(id);
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryDao.deleteCategory(id);
    }

    @Override
    public Category getCategoryBySlug(String categorySlug) {
        return categoryDao.getCategoryBySlug(categorySlug);
    }
}

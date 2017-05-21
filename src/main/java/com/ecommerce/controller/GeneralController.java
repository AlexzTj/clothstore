package com.ecommerce.controller;

import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class GeneralController {

    @ModelAttribute("uploadDir")
    public String getRootDir(){
        return "/uploads/";
    }
    @Autowired
    protected CategoryService categoryService;
    @ModelAttribute("catList")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
}

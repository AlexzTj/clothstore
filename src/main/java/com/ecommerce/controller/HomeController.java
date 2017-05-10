package com.ecommerce.controller;

import com.ecommerce.dao.CategoryDao;
import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CategoryDao categoryDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Integer id, Model model) {
        Product product = productDao.getProductById(id);
        model.addAttribute("product", product);
        return "detail";
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public String category(@PathVariable Integer id, Model model) {
        Category category = categoryDao.getCategoryById(id);
        model.addAttribute("category", category);
        return "category";
    }

    public static void main(String[] args) {
        ProductDao productDao = new ProductDao();
        CategoryDao categoryDao = new CategoryDao();
        Product product = productDao.getProductById(1);
    }
}

package com.ecommerce.controller;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    //TODO add breadcrumbs
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    //
//    @InitBinder
//    public void dataBinding(WebDataBinder binder) {
//        binder.registerCustomEditor(Double.class,new CustomNumberEditor(Double.class,true));
//    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        List<Product> list = productService.fetchHotProducts(6);
        model.addAttribute("prodList", list);
        return "index";
    }

    @RequestMapping(value = "/shopping-cart", method = RequestMethod.GET)
    public String cart() {
        return "shopping-cart";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id, true);
        model.addAttribute("product", product);
        return "detail";
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET)
    public String category(@PathVariable Integer categoryId, Model model) {
        Category category = categoryService.getCategoryById(categoryId);
        List<Product> productList = productService.getProductsByCategoryId(categoryId);
        Long productCount = productService.countProductsByCategoryId(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("productList", productList);
        model.addAttribute("productCount", productCount);
        return "category";
    }


}

package com.ecommerce.controller;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping(value = "/products/{categorySlug}", method = RequestMethod.GET)
    public String category(@PathVariable String categorySlug, Model model) {
        return categoryPaged(categorySlug, 1, model);
    }

    @RequestMapping(value = "/products/{categorySlug}/page/{pageId}", method = RequestMethod.GET)
    public String categoryPaged(@PathVariable("categorySlug") String categorySlug, @PathVariable("pageId") Integer pageId, Model model) {
        Category category = categoryService.getCategoryBySlug(categorySlug);
        if (category == null) return "404";
        if (--pageId < 0) return "404";
        int pageSize = 10;
        List<Product> productList = productService.getProductsByCategoryId(category.getId());
        PagedListHolder<Product> pagedListHolder = new PagedListHolder<>(productList);
        pagedListHolder.setPage(pageId);
        pagedListHolder.setPageSize(pageSize);
        pagedListHolder.setMaxLinkedPages(5);
        model.addAttribute("category", category);
        model.addAttribute("categorySlug", categorySlug);
        model.addAttribute("pagedListHolder", pagedListHolder);
        model.addAttribute("actualPageSize",
                pagedListHolder.getMaxLinkedPages() > pagedListHolder.getPageCount() ? pagedListHolder.getPageCount() : pagedListHolder.getMaxLinkedPages());
        return "category";
    }


    @RequestMapping(value = "/error")
    public ModelAndView handleAll(HttpServletRequest request) {
        LOGGER.error("Request: " + request.getRequestURL());

        ModelAndView mav = new ModelAndView();
        mav.addObject("reason", request.getAttribute("javax.servlet.error.message"));
        mav.addObject("status", request.getAttribute("javax.servlet.error.status_code"));
        mav.setViewName("error");
        return mav;
    }

    @RequestMapping(value = "/403")
    public String handle403(HttpServletRequest request) {
        LOGGER.error("Access denied: " + request.getRequestURL());

        return "403";
    }
}

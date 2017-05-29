package com.ecommerce.controller;

import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class CategoryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/admin/categories")
    public String viewCategories(){
        return "maintainCategories";
    }

    @RequestMapping(value = "/admin/editCategory/{id}", method = RequestMethod.GET)
    public String editCategoryGet(@PathVariable Integer id, Model model, HttpServletRequest req) {
        Category category = categoryService.getCategoryById(id);
        //if null redirect
        model.addAttribute("category", category);
        return "editCategory";
    }


    @RequestMapping(value = "/admin/editCategory", method = RequestMethod.POST)
    public String editCategoryPost(@Valid @ModelAttribute("category") Category category,
                                  BindingResult bindingResult,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "editCategory";
        }
        try {
            categoryService.updateCategory(category);
        } catch (Exception dsa) {
            LOGGER.error("failed to edit category ", dsa);
            model.addAttribute("errMsg", "errors where found " + dsa.getMessage());
            return "editCategory";
        }

        redirectAttributes.addFlashAttribute("sucMsg",
                "You successfully edited category " + category.getName());
        return "redirect:/admin/categories";
    }

    @RequestMapping(value = "/admin/addCategory", method = RequestMethod.GET)
    public String addProductGet(Model model) {
        model.addAttribute("category", new Category());
        return "addCategory";
    }

    @RequestMapping(value = "/admin/addCategory", method = RequestMethod.POST)
    public String addCategoryPost(@Valid @ModelAttribute("category") Category category,
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "addCategory";
        }
        try {
            categoryService.addCategory(category);
        } catch (Exception dsa) {
            LOGGER.error("failed to save category ", dsa);
            model.addAttribute("errMsg", "errors where found " + dsa.getMessage());
            return "addCategory";
        }

        redirectAttributes.addFlashAttribute("sucMsg",
                "You successfully created category " + category.getName());
        return "redirect:/admin/categories";
    }

    @RequestMapping(value = "/admin/deleteCategory/{id}", method = RequestMethod.POST)
    public RedirectView deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return new RedirectView("/admin/categories");
    }
}
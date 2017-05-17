package com.ecommerce.controller;

import com.ecommerce.dao.CategoryDao;
import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);
    //TODO add breadcrumbs
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryDao categoryDao;

    @Value("${upload.dir}")
    private String rootDir;

    @ModelAttribute("uploadDir")
    public String getRootDir(){return rootDir;}

    @ModelAttribute("catList")
    public List<Category> getAllCategories() {
        return categoryDao.getAllCategories();
    }
//
//    @InitBinder
//    public void dataBinding(WebDataBinder binder) {
//        binder.registerCustomEditor(Double.class,new CustomNumberEditor(Double.class,true));
//    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model) {
        List<Product> list = productService.fetchProductsWithFeaturedImage(6);
        model.addAttribute("prodList",list);
        return "index";
    }

    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    public String detail(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "detail";
    }

    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    public String category(@PathVariable Integer id, Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "category";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Model model) {
        List<Product> list = productService.getAllProducts();
        model.addAttribute("prodList", list);
        return "admin";
    }

    @RequestMapping(value = "/admin/addProduct", method = RequestMethod.GET)
    public String addProductGet(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @RequestMapping(value = "/admin/addProduct", method = RequestMethod.POST)
    public String addProductPost(@Valid @ModelAttribute("product") Product product,
                                 BindingResult bindingResult,
                                 @RequestParam("image") MultipartFile[] images,
                                 @RequestParam("featured") MultipartFile featuredImage,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            return "addProduct";
        }
        List<MultipartFile> imagesList = Arrays.stream(images).collect(Collectors.toList());
        imagesList.add(featuredImage);
        try{
            productService.addProductWithImages(product, imagesList);
        }catch (Exception dsa){
            LOGGER.error("failed to save product ",dsa);
            model.addAttribute("errMsg", "errors where found "+dsa.getMessage());
            return "addProduct";
        }

        redirectAttributes.addFlashAttribute("sucMsg",
                "You successfully created product " + product.getTitle());
        return "redirect:/admin";
    }


    @RequestMapping(value = "/admin/deleteProduct/{id}", method = RequestMethod.POST)
    public String deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

}

package com.ecommerce.controller;

import com.ecommerce.model.Image;
import com.ecommerce.model.ImageType;
import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AdminController extends GeneralController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
    private JsonFactory factory = new JsonFactory();

    @Autowired
    private ProductService productService;

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

    @RequestMapping(value = "/admin/editProduct/{id}", method = RequestMethod.GET)
    public String editProductGet(@PathVariable Integer id, Model model, HttpServletRequest req) {
        Product product = productService.getProductById(id, true);
        //if null redirect
        model.addAttribute("product", product);
        Optional<Image> featured = product.getImageMetaSet().stream().filter(i -> i.getType() == ImageType.FEATURED).findFirst();
        String featuredImgJosn = null;
        if (featured.isPresent()) {
            featuredImgJosn = imageToJson(Collections.singletonList(featured.get()));
        }
        List<Image> gallery = product.getImageMetaSet().stream().filter(i -> i.getType() == ImageType.GALLERY).collect(Collectors.toList());

        model.addAttribute("featuredImgJson", featuredImgJosn);
        model.addAttribute("galleryImgJson", imageToJson(gallery));
        return "editProduct";
    }

    private String imageToJson(List<Image> gallery) {
        StringWriter stringWriter = new StringWriter();
        try (JsonGenerator generator = factory.createJsonGenerator(stringWriter)) {
            generator.writeStartArray();
            for (Image img : gallery) {
                generator.writeStartObject();
                generator.writeStringField("name", img.getId().toString());
                generator.writeStringField("type", "image/" + StringUtils.getFilenameExtension(img.getImagePath()));
                generator.writeStringField("file", "/uploads/" + img.getImagePath());
                generator.writeEndObject();
            }
            generator.writeEndArray();
            generator.flush();
        } catch (IOException e) {
            stringWriter.getBuffer().setLength(0);
            LOGGER.error("error during converting to json", e);
        }
        return stringWriter.toString();
    }

    @RequestMapping(value = "/admin/editProduct", method = RequestMethod.POST)
    public String editProductPost(@Valid @ModelAttribute("product") Product product,
                                  BindingResult bindingResult,
                                  @RequestParam("files[]") List<MultipartFile> imagesList,
                                  @RequestParam("featured[]") List<MultipartFile> featuredImage,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "editProduct";
        }
        try {
            imagesList.addAll(featuredImage);
            productService.updateProduct(product, imagesList);
        } catch (Exception dsa) {
            LOGGER.error("failed to edit product ", dsa);
            model.addAttribute("errMsg", "errors where found " + dsa.getMessage());
            return "editProduct";
        }

        redirectAttributes.addFlashAttribute("sucMsg",
                "You successfully edited product " + product.getTitle());
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/addProduct", method = RequestMethod.POST)
    public String addProductPost(@Valid @ModelAttribute("product") Product product,
                                 BindingResult bindingResult,
                                 @RequestParam("image") List<MultipartFile> imagesList,
                                 @RequestParam("featured") MultipartFile featuredImage,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "addProduct";
        }
        imagesList.add(featuredImage);
        try {
            productService.addProductWithImages(product, imagesList);
        } catch (Exception dsa) {
            LOGGER.error("failed to save product ", dsa);
            model.addAttribute("errMsg", "errors where found " + dsa.getMessage());
            return "addProduct";
        }

        redirectAttributes.addFlashAttribute("sucMsg",
                "You successfully created product " + product.getTitle());
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/deleteProduct/{id}", method = RequestMethod.POST)
    public RedirectView deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new RedirectView("/admin");
    }
}
package com.ecommerce.controller;

import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@ControllerAdvice
class GlobalController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalController.class);
    @ModelAttribute("uploadDir")
    public String getRootDir(){
        return "/uploads/";
    }
    @Autowired
    private CategoryService categoryService;
    /*used in menus as well*/
    @ModelAttribute("catList")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle() {
        return "404";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value= HttpStatus.BAD_REQUEST,reason = "illegal request, please verify your upload")
    public void handleClientErrors(){}

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="There was an error processing the request body.")
    public void handleMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException exception) {
        LOGGER.error("\nUnable to bind post data sent to: " + request.getRequestURI() + "\nCaught Exception:\n" + exception.getMessage());
    }

    @ExceptionHandler({RuntimeException.class,Exception.class})
    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR,reason = "internal server error")
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e){
        LOGGER.error("Application error ",e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("reason", request.getAttribute("javax.servlet.error.message"));
        mav.addObject("status", request.getAttribute("javax.servlet.error.status_code"));
        mav.setViewName("error");
        return mav;
    }
}

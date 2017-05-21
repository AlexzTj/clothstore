package com.ecommerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
class GlobalDefaultExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle() {
        return "404";
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e){
        LOGGER.error("Application error ",e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("reason", request.getAttribute("javax.servlet.error.message"));
        mav.addObject("status", request.getAttribute("javax.servlet.error.status_code"));
        mav.setViewName("error");
        return mav;
    }
}

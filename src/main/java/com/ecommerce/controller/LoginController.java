package com.ecommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error
            , @RequestParam(value = "logout", required = false) String logout) {
        ModelAndView mw = new ModelAndView("login");
        if (error != null) {
            mw.addObject("errMsg", "Username or password are invalid");
        } else if (logout != null) {
            mw.addObject("errMsg", "You have been successfully logout");
        }

        return mw;
    }
}

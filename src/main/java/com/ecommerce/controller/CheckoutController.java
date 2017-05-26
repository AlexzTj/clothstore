package com.ecommerce.controller;


import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class CheckoutController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/checkout")
    public String checkout(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes, Principal principal){
        Cart cart = (Cart) request.getSession(true).getAttribute("cart");
        if (cart == null || cart.getCartItems().isEmpty()) {
            redirectAttributes.addFlashAttribute("emptyCart","Please add products to your cart");
            return "redirect:/";
        }
        String name = principal.getName();

        User user = userService.getUserById(name);
        if (user==null) throw new IllegalArgumentException();
        if (user==null) throw new IllegalArgumentException();
        Order order = new Order();
        order.setUser(user);
        order.setCart(cart);
        order.setCartItems(new ArrayList<>(cart.getCartItems().values()));
        model.addAttribute("order",order);
        return "checkout";
    }
}

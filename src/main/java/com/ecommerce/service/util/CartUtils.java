package com.ecommerce.service.util;


import com.ecommerce.model.Cart;

import javax.servlet.http.HttpServletRequest;

public final class CartUtils {
    public static Cart getCartInSession(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession(true).getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("cart", cart);
        }
        return cart;
    }

    public static void deleteCart(HttpServletRequest request) {
        request.getSession().removeAttribute("cart");
    }
}

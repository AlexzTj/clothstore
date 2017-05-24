package com.ecommerce.controller;

import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.dto.ProductDTO;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.util.CartUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/rest/cart")
public class ShoppingCartController {
    @Autowired
    private ProductService productService;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Cart getCart(HttpServletRequest request) {
        return CartUtils.getCartInSession(request);
    }

    @RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addToCart(@PathVariable("productId") Integer productId, HttpServletRequest request) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            Cart cart = CartUtils.getCartInSession(request);
            CartItem cartItem = new CartItem(product);
            cart.addCartItem(cartItem);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @RequestMapping(value = "/remove/{productId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromCart(@PathVariable("productId") Integer productId, HttpServletRequest request) {
        Product product = productService.getProductById(productId);

        if (product != null) {
            Cart cart = CartUtils.getCartInSession(request);
            CartItem cartItem = new CartItem(product);
            cart.removeCartItem(cartItem);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCart(@RequestBody List<ProductDTO> payload, HttpServletRequest request) {
        Cart cartInSession = (Cart) request.getSession(true).getAttribute("cart");
        if (cartInSession == null || payload == null)
            throw new IllegalArgumentException("cart update failed, nothing to update");
        cartInSession.updateQuantity(payload);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(HttpServletRequest request) {
        CartUtils.deleteCart(request);
    }
}

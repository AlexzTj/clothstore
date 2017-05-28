package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/admin/orders", method = RequestMethod.GET)
    public String getOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @RequestMapping("/admin/orders/{orderId}")
    public String getOrder(@PathVariable("orderId") Integer orderId, Model model) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) throw new IllegalArgumentException();
        model.addAttribute("order", order);
        return "order-detail";
    }


    @RequestMapping(value = "/admin/updateOrder", method = RequestMethod.POST)
    public String updateOrder(@ModelAttribute("order") Order order, Model model, RedirectAttributes redirectAttributes) {
        orderService.updateOrderStatus(order.getId(),order.getStatus());
        redirectAttributes.addFlashAttribute("sucMsg",
                "You successfully updated order # "+order.getId() +", with status " + order.getStatus());
        return "redirect:/admin/orders";
    }
}

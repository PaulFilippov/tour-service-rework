package com.paul.controllers;

import com.paul.entities.Order;
import com.paul.services.OrderService;
import com.paul.services.TourService;
import com.paul.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class OrderController {

    OrderService orderService;
    TourService tourService;
    UserService userService;

    @Autowired
    public OrderController(OrderService orderService, TourService tourService, UserService userService) {
        this.orderService = orderService;
        this.tourService = tourService;
        this.userService = userService;
    }

    @GetMapping(value = "/orders")
    public ModelAndView showUserOrders() {
        Set<Order> userOrders = orderService.getUserOrders(userService.getCurAuthUser());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orders");
        modelAndView.addObject("userOrders", userOrders);
        modelAndView.addObject("tourService", tourService);
        return modelAndView;
    }

    @GetMapping(value = "/deleteOrder/{id_order}")
    public String deleteOrder(@PathVariable("id_order") Long id_order) {
        orderService.deleteOrder(id_order);
        return "redirect:/orders";
    }
}

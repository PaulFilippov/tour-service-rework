package com.paul.controllers;

import com.paul.entities.Tour;
import com.paul.entities.User;
import com.paul.services.OrderService;
import com.paul.services.TourService;
import com.paul.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.Set;

@Controller
public class TourController {

    private TourService tourService;
    private OrderService orderService;
    private UserService userService;

    @Autowired
    public TourController(TourService tourService, OrderService orderService, UserService userService) {
        this.tourService = tourService;
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping(value = "/tours")
    public String showAllTours(ModelMap model) {
        Set<Tour> allTours = tourService.getAllTour();
        User curUser = userService.getCurAuthUser();
        model.addAttribute("allTours", allTours);
        model.addAttribute("tourService", tourService);
        model.addAttribute("curUser", curUser);
        return "tours";
    }

    //добавить выбранный тур в заказы текущего юзера
    @GetMapping(value = "/orderTourByUser/{id_tour}")
    public String addTourToUser(@PathVariable("id_tour") Long id_tour) {
        Tour selectedTour = tourService.getTourById(id_tour);
        orderService.createNewOrderForCurrAuthUser(selectedTour);
        return "redirect:/tours";
    }


}

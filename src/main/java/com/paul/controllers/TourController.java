package com.paul.controllers;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paul.entities.Order;
import com.paul.entities.Tour;
import com.paul.entities.User;
import com.paul.services.OrderService;
import com.paul.services.TourService;
import com.paul.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

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
        Set<Order> ordersUser = orderService.getUserOrders(curUser);



        Map <String, String> mapSelectedTickets = new HashMap<>();
        for (Order order:ordersUser) {
            int counOfReserveTickets = order.getCountReservedByUser();
            System.out.println(counOfReserveTickets);
            mapSelectedTickets.put(Long.toString(order.getTourId()),Integer.toString(order.getCountReservedByUser()));
        }

        mapSelectedTickets.forEach((key, value) -> System.out.println(key + ":" + value));

        model.addAttribute("mapSelectedTickets", mapSelectedTickets);
        model.addAttribute("allTours", allTours);
        model.addAttribute("tourService", tourService);
        model.addAttribute("ordersUser", ordersUser);
        model.addAttribute("curUser", curUser);
        return "tours";
    }

    //добавить выбранный тур в заказы текущего юзера
    @PostMapping(value = "/orderTourByUser")
    public String addTourToUser(HttpServletRequest request) {

        List<String> listAtr = Collections.list(request.getParameterNames());
        listAtr.stream().forEach(System.out::println);

        int id_tour = Integer.parseInt(request.getParameter("id_tour"));
        int numberOfTickets = Integer.parseInt(request.getParameter("selectNumberOfTicketsByUserID"+id_tour));

        Tour selectedTour = tourService.getTourById((long) id_tour);
        orderService.createNewOrderForCurrAuthUser(selectedTour, numberOfTickets);
        return "redirect:/tours";

    }

    @ResponseBody
    @GetMapping(value = "/tourss")
    public Set<Tour> showAllTours1() {
        Set<Tour> allTours = tourService.getAllTour();
        return allTours;
    }

}

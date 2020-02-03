package com.paul.services;

import com.paul.entities.Order;
import com.paul.entities.Tour;
import com.paul.entities.User;
import com.paul.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private OrderRepository orderRep;
    private TourService tourService;
    private UserService userService;

    @Autowired
    public OrderService(OrderRepository orderRep, TourService tourService, UserService userService) {
        this.orderRep = orderRep;
        this.tourService = tourService;
        this.userService = userService;
    }

    public Set<Order> getAllOrder() {
        Set<Order> allOrder = new HashSet();
        //cast from Iterable in Set
        orderRep.findAll().iterator().forEachRemaining(allOrder::add);
        return allOrder;
    }

    // создание нового заказанного тура для текущегор авторизованного юзера
    public void createNewOrderForCurrAuthUser(Tour tour, int numberOfTickets) {
        if (numberOfTickets > 0) {
            User currUser = userService.getCurAuthUser();
            Order order = new Order(currUser, tour, numberOfTickets);
            orderRep.save(order);
        }
    }

    public Order getOrderById(Long id_order) {
        return orderRep.findById(id_order).get();
    }

    public void deleteOrder(Long id_order) {
        orderRep.deleteById(id_order);
    }

    public Set<Order> getUserOrders(User user) {
        Set<Order> userOrders = new HashSet();
        userOrders = user.getUserOrders();
        return userOrders;
    }

    //проверяет есть ли тур уже в заказе у юзера
    public boolean checkTourInUserOrders(User user, Tour tour) {
        return user.getUserOrders()
                .stream()
                .map(order -> order.getTour())
                .collect(Collectors.toSet())
                .contains(tour);
    }
}

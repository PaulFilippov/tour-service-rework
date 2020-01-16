package com.paul.services;

import com.paul.entities.Order;
import com.paul.entities.Tour;
import com.paul.entities.User;
import com.paul.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class TourService {

    private TourRepository tourRep;

    @Autowired
    public TourService(TourRepository tourRep) {
        this.tourRep = tourRep;
    }

    public Set<Tour> getAllTour() {
        Set<Tour> allTours = new HashSet();
        //cast from Iterable in Set
        tourRep.findAll().iterator().forEachRemaining(allTours::add);
        return allTours;
    }

    public Tour getTourById(Long tourId) {
        return tourRep.findById(tourId).get();
    }

    //число свободных мест в туре
    public int getNumberOfFreePlaces(Tour tour) {
        int count_limit = tour.getCount_limit();
        Set<Order> ordersOfTour = tour.getOrdersOfTour();
        int booked_places = ordersOfTour.size();
        int numberFreePlaces = count_limit - booked_places;
        return numberFreePlaces;
    }

    //проверка прошел ли уже тур
    public boolean isPassedTour(Tour tour) {
        Date currDate = new Date();
        Date startDate = tour.getStart_date();
        Date endDate = tour.getEnd_date();
        if (startDate.after(currDate) && endDate.after(currDate)) {
            return false;
        } else {
            return true;
        }
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

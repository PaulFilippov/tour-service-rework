package com.paul;

import com.paul.entities.*;
import com.paul.repositories.*;
import com.paul.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Transactional
public class DatabaseLoader implements ApplicationRunner {

    private UserRepository userRepository;
    private TourRepository tourRepository;
    private OrderRepository orderRep;
    private TourService tourService;
    private UserService userService;


    public DatabaseLoader(UserRepository userRepository, TourRepository tourRepository, OrderRepository orderRep, TourService tourService, UserService userService) {
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.orderRep = orderRep;
        this.tourService = tourService;
        this.userService = userService;
    }

    public void run(ApplicationArguments args) {
        System.out.println("аплкатион раннер старт");
        generateUsersInBD();
        generateToursInBD();
        generateOldToursInBD();
        generateOrdersOfOldToursForUsers();
        System.out.println("аплкатион раннер стоп");
    }

    public void generateUsersInBD() {
        Date dateBirth = new Date();
        for (int i = 0; i < 10; i++) {
            Calendar cb = Calendar.getInstance();
            cb.setTime(dateBirth);
            cb.add(Calendar.YEAR, -3);
            dateBirth = cb.getTime();
            User temp = new User(
                    "name" + Integer.toString(i),
                    "last_name",
                    "email" + Integer.toString(i),
                    "pass" + Integer.toString(i),
                    true,
                    dateBirth,
                    UserRole.USER
            );
            userRepository.save(temp);
        }
    }

    public void generateToursInBD() {
        Date dt_start = new Date();
        Date dt_end = new Date();
        for (int i = 0; i < 10; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(dt_start);
            c.add(Calendar.MONTH, 1);
            dt_start = c.getTime();
            c.add(Calendar.DATE, 10);
            dt_end = c.getTime();
            Tour temp = new Tour(
                    "travel" + Integer.toString(i),
                    "awesome_trip" + Integer.toString(i),
                    "planet Earth" + Integer.toString(i),
                    dt_start,
                    dt_end,
                    i
            );
            tourRepository.save(temp);
        }
        System.out.println("Туры сгенерированы");
    }

    //генерация прошедших туров
    public void generateOldToursInBD() {
        Date dt_start = new Date();
        Date dt_end = new Date();
        for (int i = 0; i < 10; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(dt_start);
            c.add(Calendar.MONTH, -1);
            dt_start = c.getTime();
            c.add(Calendar.DATE, -10);
            dt_end = c.getTime();
            Tour temp = new Tour(
                    "trip" + Integer.toString(i),
                    "good_trip" + Integer.toString(i),
                    "space" + Integer.toString(i),
                    dt_start,
                    dt_end,
                    i
            );
            tourRepository.save(temp);
        }
        System.out.println("Прошедшие туры сгенерированы");
    }

    public void generateOrdersOfOldToursForUsers() {
        for (Long i = 0L; i < 10; i++) {
            int numOrders = 0;
            for (Long j = i+10; j < 20*2; j++) {
                if (userRepository.findById(i).orElse(null) == null) {
                    break;
                } else if (tourRepository.findById(j).orElse(null) == null) {
                    continue;
                } else {
                    User user = userRepository.findById(i).get();
                    Tour tour = tourRepository.findById(j).get();
                    if (tourService.getNumberOfFreePlaces(tour) <= 0) {
                        continue;
                    }
                    int countReserveByUser = 1;
                    Order order = new Order(user, tour, countReserveByUser);
                    orderRep.save(order);
                    numOrders++;
                    if (numOrders == 3) {
                        break;
                    }
                }
            }
        }
        System.out.println("Заказанные туры для пользователей добавлены");
    }

}

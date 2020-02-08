package com.paul.repositories;

import com.paul.entities.Order;
import com.paul.entities.Tour;
import com.paul.entities.User;
import com.paul.entities.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //контекст пересоздается перед каждым методом теста (использовал для пересоздания базы перед каждым тестом)
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TourRepository tourRepository;
    @Autowired
    EntityManager entityManager;
    Order order;


    @BeforeEach
    void setUp() {

        //Для создания заказана необхожимо создать родительские сущности юзера и тура
       User user = new User(
                "name",
                "last_name",
                "email",
                "pass",
                true,
                new Date(),
                UserRole.USER
        );
        entityManager.persist(user);
        entityManager.flush();

        Date dt_start = new Date();
        Date dt_end = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt_start);
        c.add(Calendar.MONTH, 1);
        dt_start = c.getTime();
        c.add(Calendar.DATE, 10);
        dt_end = c.getTime();
        Tour tour = new Tour(
                "exTravel",
                "exAwesome_trip",
                "exPlanet Earth",
                dt_start,
                dt_end,
                1
        );
        entityManager.persist(tour);
        entityManager.flush();

        order = new Order(user, tour, 1);
        entityManager.persist(tour);
        entityManager.flush();

    }

    @Test
    void when_get_orderById_return_not_null() {

// Arrange - in set up the test, mock out all dependencies, use dummy data.
        Order found;

// Act - run the condition that you want to test, which should be contained in one function.
        found = orderRepository.findById(1L).orElseThrow(() ->
                new NullPointerException("order with id " + order.getId_order() + " not found"));;

// Assert - check that the results of the action change the data in the way you predicted.
        assertNotNull(found);

    }


    @Test
    void when_getAllOrders_return_allOrders_fromDB() {

// Arrange - in set up the test, mock out all dependencies, use dummy data.
        Set<Order> expectedAllOrdersSet = new HashSet<Order>(){{
            add(order);
        }};
        Set <Order> actualAllOrdersSet = new HashSet<>();

// Act - run the condition that you want to test, which should be contained in one function.
        orderRepository.findAll().forEach(actualAllOrdersSet::add);

// Assert - check that the results of the action change the data in the way you predicted.
        assertEquals(expectedAllOrdersSet,actualAllOrdersSet);

    }

    @AfterEach
    void tearDown() {
    }
}
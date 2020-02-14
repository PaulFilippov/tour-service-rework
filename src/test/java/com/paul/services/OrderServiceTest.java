package com.paul.services;

import com.paul.entities.*;
import com.paul.repositories.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;

import java.nio.channels.UnresolvedAddressException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //контекст пересоздается перед каждым методом теста (использовал для пересоздания базы перед каждым тестом)
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserService userService;

    @InjectMocks
    OrderService orderService;

    private List<Order> listOrders = new ArrayList<>();
    private List<Tour> listTours = new ArrayList<>();
    private List<User> listUsers = new ArrayList<>();


    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        for (int i = 1; i < 3; i++) {

            Tour expecTour = new Tour(
                    "travel" + Integer.toString(i),
                    "awesome_trip" + Integer.toString(i),
                    "planet Earth" + Integer.toString(i),
                    new Date(),
                    new Date(),
                    5
            );
            listTours.add(expecTour);

            User expecUser = new User(
                    "name" + Integer.toString(i),
                    "last_name",
                    "email" + Integer.toString(i),
                    "pass" + Integer.toString(i),
                    true,
                    new Date(),
                    UserRole.USER
            );
            listUsers.add(expecUser);

            Order order = new Order(expecUser, expecTour, 1);
            listOrders.add(order);

        }

    }

    @Test
    void getAllOrder() {

// Arrange - in set up the test, mock out all dependencies, use dummy data.
        List<Order> expectedOrders = listOrders;
        List<Order> actualOrders = new ArrayList<>();
        when(orderRepository.findAll()).thenReturn(expectedOrders);

// Act - run the condition that you want to test, which should be contained in one function.
        orderRepository.findAll().iterator().forEachRemaining(actualOrders::add);

// Assert - check that the results of the action change the data in the way you predicted.
        assertEquals(expectedOrders, actualOrders);

    }

    @Test
    void createNewOrderForCurrAuthUser() {

        Tour tour = listTours.get(1);
        Order order = listOrders.get(1);
        User user = listUsers.get(1);
        when(userService.getCurAuthUser()).thenReturn(user);

        orderService.createNewOrderForCurrAuthUser(tour, 1);

        verify(orderRepository, atLeastOnce()).save(order);

    }

    @Test
    void getOrderById() {

        Order expectedOrder = listOrders.get(1);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(expectedOrder));

        Order actualOrder = orderService.getOrderById(1L);

        assertEquals(expectedOrder, actualOrder);
    }

    @Test
    void deleteOrder() {

        Long idOfDeleteOrder = 1L;
        Order order = listOrders.get(idOfDeleteOrder.intValue());

        orderService.deleteOrder(idOfDeleteOrder);

        verify(orderRepository, atLeastOnce()).deleteById(idOfDeleteOrder);

    }

    @Test
    void getUserOrders() {

        List<Order> expectedUserOrders = new ArrayList<>();
        expectedUserOrders.add(listOrders.get(1));
        List<Order> actualUserOrders;

        actualUserOrders = new ArrayList<>(orderService.getUserOrders(listUsers.get(1)));

        assertTrue(equalsForLists(expectedUserOrders, actualUserOrders));

    }

    private static boolean equalsForLists(List<?> list1, List<?> list2){
        if(list1 == null || list2 ==null){
            return false;
        }
        if(list1.size()!=list2.size()){
            return false;
        }
        return list1.containsAll(list2);
    }


    @AfterEach
    void tearDown() {

    }
}
package com.paul.services;

import com.paul.entities.Order;
import com.paul.entities.Tour;
import com.paul.entities.User;
import com.paul.entities.UserRole;
import com.paul.repositories.TourRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.annotation.DirtiesContext;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //контекст пересоздается перед каждым методом теста (использовал для пересоздания базы перед каждым тестом)
class TourServiceTest {

    @Mock
    TourRepository tourRepository;

    @InjectMocks
    TourService tourService;

    private Set<Tour> expectedTour = new HashSet<>();


    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        Date dt_start = new Date();
        Date dt_end = new Date();
        for (int i = 1; i < 2; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(dt_start);
            c.add(Calendar.MONTH, 1);
            dt_start = c.getTime();
            c.add(Calendar.DATE, 10);
            dt_end = c.getTime();
            Tour tour = new Tour(
                    "travel" + Integer.toString(i),
                    "awesome_trip" + Integer.toString(i),
                    "planet Earth" + Integer.toString(i),
                    dt_start,
                    dt_end,
                    i
            );
            expectedTour.add(tour);
        }

    }

    @Test
    void getAllTour() {

        when(tourRepository.findAll()).thenReturn(expectedTour);

//        assertThat(equalsForSets(expectedTour, tourService.getAllTour()), is(true));
        assertEquals(expectedTour, tourService.getAllTour());
        verify(tourRepository, times(1)).findAll();

    }

    private static boolean equalsForSets(Set<?> set1, Set<?> set2){
        if(set1 == null || set2 ==null){
            return false;
        }
        if(set1.size()!=set2.size()){
            return false;
        }
        return set1.containsAll(set2);
    }

    @Test
    void getTourById() {

        List<Tour> listTours = new ArrayList<>(expectedTour);
//        Tour tour = listTours.get(0);
        when(tourRepository.findById(1L)).thenReturn(Optional.of(listTours.get(0)));
        Set<Tour> actualTours = new HashSet<>();
        Tour actualTour;

        //act
        actualTour = (Tour) tourService.getTourById(1L);
        actualTours.add(actualTour);
        boolean expecContainsActual = actualTours.containsAll(expectedTour.stream().collect(Collectors.toSet()));

        assertTrue(expecContainsActual);
        assertEquals(1, actualTours.size());
        verify(tourRepository, times(1)).findById(1L);

    }

    @Test
    void getNumberOfFreePlaces() {

        Tour tour = new Tour(
                "travel",
                "awesome_trip",
                "planet Earth",
                new Date(),
                new Date(),
                5
        );

//        Order orderMock = mock(Order.class);
//        when(orderMock.getCountReservedByUser()).thenReturn(1);

        int actualNumberFreePlaces = tourService.getNumberOfFreePlaces(tour);

        assertEquals(5, actualNumberFreePlaces);

    }

    @Test
    void isPassedTour() {

        // Arrange - in set up the test, mock out all dependencies, use dummy data.
        Tour actualTour = new Tour(
                "travel",
                "awesome_trip",
                "planet Earth",
                new Date(),
                new Date(),
                5
        );

// Act - run the condition that you want to test, which should be contained in one function.
       boolean actual = tourService.isPassedTour(actualTour);

// Assert - check that the results of the action change the data in the way you predicted.
        assertTrue(actual);

    }

    @Test
    void checkTourInUserOrders() {

        Tour expecTour = new Tour(
                "travel",
                "awesome_trip",
                "planet Earth",
                new Date(),
                new Date(),
                5
        );
       User expecUser = new User(
                "name2",
                "last_name2",
                "email2",
                "pass2",
                true,
                new Date(),
                UserRole.USER
        );

// Act - run the condition that you want to test, which should be contained in one function.
        boolean actual = tourService.checkTourInUserOrders(expecUser, expecTour);

// Assert - check that the results of the action change the data in the way you predicted.
        assertFalse(actual);

    }

    @AfterAll
    static void afterAll() {

    }

}
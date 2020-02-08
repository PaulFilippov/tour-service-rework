package com.paul.repositories;

import com.paul.entities.Tour;
import com.paul.entities.User;
import com.paul.entities.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
class TourRepositoryTest {

    @Autowired
    TourRepository tourRepository;
    @Autowired
    EntityManager entityManager;
    Tour tour;


    @BeforeEach
    void setUp() {

        Date dt_start = new Date();
        Date dt_end = new Date();

            Calendar c = Calendar.getInstance();
            c.setTime(dt_start);
            c.add(Calendar.MONTH, 1);
            dt_start = c.getTime();
            c.add(Calendar.DATE, 10);
            dt_end = c.getTime();
            tour = new Tour(
                    "exTravel",
                    "exAwesome_trip",
                    "exPlanet Earth",
                    dt_start,
                    dt_end,
                    1
            );

        entityManager.persist(tour);
        entityManager.flush();

    }

    @Test
    void when_get_tourById_return_not_null() {

// Arrange - in set up the test, mock out all dependencies, use dummy data.
        Tour found;

// Act - run the condition that you want to test, which should be contained in one function.
        found = tourRepository.findById(1L).orElseThrow(() ->
                new NullPointerException("tour with id " + tour.getId_tour() + " not found"));;

// Assert - check that the results of the action change the data in the way you predicted.
        assertNotNull(found);

    }


    @Test
    void when_getAllTours_return_allTours_fromDB() {

// Arrange - in set up the test, mock out all dependencies, use dummy data.
        Set<Tour> expectedAllToursSet = new HashSet<Tour>(){{
            add(tour);
        }};
        Set <Tour> actualAllToursSet = new HashSet<>();

// Act - run the condition that you want to test, which should be contained in one function.
        tourRepository.findAll().forEach(actualAllToursSet::add);

// Assert - check that the results of the action change the data in the way you predicted.
        assertEquals(expectedAllToursSet,actualAllToursSet);

    }


    @AfterEach
    void tearDown() {
    }
}
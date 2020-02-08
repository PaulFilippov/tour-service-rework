package com.paul.repositories;

import com.paul.entities.User;
import com.paul.entities.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) //контекст пересоздается перед каждым методом теста (использовал для пересоздания базы перед каждым тестом)
class UserRepositoryTest {

    @Autowired
    TestEntityManager entityManager;
    @Autowired
    UserRepository userRepository;
    User user;


    @BeforeEach
    void setUp() {

        user = new User(
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

    }


    @Test
    void when_get_userById_return_not_null() {

// Arrange - in set up the test, mock out all dependencies, use dummy data.
        User found;

// Act - run the condition that you want to test, which should be contained in one function.
          found = userRepository.findById(1L).orElseThrow(() ->
                new NullPointerException("user with id " + user.getId_user() + " not found"));;

// Assert - check that the results of the action change the data in the way you predicted.
        assertNotNull(found);

    }


    @Test
    void when_getAllUsers_return_allUser_fromDB() {

// Arrange - in set up the test, mock out all dependencies, use dummy data.
        Set<User> expectedAllUsersSet = new HashSet<User>(){{
            add(user);
        }};
        Set <User> actualAllUsersSet = new HashSet<>();

// Act - run the condition that you want to test, which should be contained in one function.
        userRepository.findAll().forEach(actualAllUsersSet::add);

// Assert - check that the results of the action change the data in the way you predicted.
        assertEquals(expectedAllUsersSet,actualAllUsersSet);
        assertNotSame(expectedAllUsersSet,actualAllUsersSet);

    }


    @Test
    void testfindUserByEmail() {

        // arrange
        User found;

        //act
        found = userRepository.findUserByEmail(user.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("user with email " + user.getEmail() + " not found"));

        // assert
        assertEquals(user, found);

    }


    @AfterEach
    void afterAll() {

//        entityManager.clear();
//        userRepository.trunc();
//        entityManager.clear();
//        entityManager.flush();

    }

}
package com.paul.services;


import com.paul.entities.User;
import com.paul.entities.UserRole;
import com.paul.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {


    @InjectMocks
    private UserService userService;

//    @Mock (answer = Answers.RETURNS_DEEP_STUBS)
    @Mock
    private UserRepository userRepository;

    private Set<User> expectedUser = new HashSet<>();

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this); //without this you will get NPE on mock userService
//        UserRepository userRepositoryMock = Mockito.mock(UserRepository.class);
//        userService = new UserService(userRepositoryMock);

        User user;
        Date dateBirth = new Date();
        for (int i = 1; i < 2; i++) {
            Calendar cb = Calendar.getInstance();
            cb.setTime(dateBirth);
            cb.add(Calendar.YEAR, -3);
            dateBirth = cb.getTime();
            user = new User(
                    "name" + Integer.toString(i),
                    "last_name",
                    "email" + Integer.toString(i),
                    "pass" + Integer.toString(i),
                    true,
                    dateBirth,
                    UserRole.USER
            );
            user.setId_user(1L);
            expectedUser.add(user);
        }

    }


    @Test
    void getAllUser() {

        when(userRepository.findAll()).thenReturn(expectedUser);

        assertThat(equalsForSets(expectedUser, userService.getAllUser()), is(true));
//        assertEquals(expectedUser, userService.getAllUser());
        verify(userRepository, times(1)).findAll();

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
    void loadUserByUsername() {

        Iterator iterator = expectedUser.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
       Optional<User> expecUser = Optional.of(new User(
                "name2",
                "last_name2",
                "email2",
                "pass2",
                true,
                new Date(),
                UserRole.USER
        ));
       when(userRepository.findUserByEmail("email2")).thenReturn(expecUser);
        Set<User> actualUser = new HashSet<>();
        User actualUsers;

        //act
        actualUsers = (User) userService.loadUserByUsername("email2");
        actualUser.add(actualUsers);
        boolean expecContainsActual = actualUser.containsAll(expecUser.stream().collect(Collectors.toSet()));

        assertTrue(expecContainsActual);
        assertEquals(1, actualUser.size());
        verify(userRepository, times(1)).findUserByEmail("email2");

    }


    @Test
    void getCurrentUserEmail() {

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("email1");
        String expectedEmail = "email1";

        //act
        String actualEmail = userService.getCurrentUserEmail();

        assertEquals(expectedEmail, actualEmail);

    }


    @Test
    void saveUserProfile() {

        User user = new User(
                "name" ,
                "last_name",
                "email",
                "pass",
                true,
                new Date(),
                UserRole.USER
        );

        //act
        userService.saveUserProfile(user);

        verify(userRepository).save(user);

    }


    @Test
    void getCurAuthUser() {

        Optional<User> expecUser = Optional.of(new User(
                "name2",
                "last_name2",
                "email2",
                "pass2",
                true,
                new Date(),
                UserRole.USER
        ));
        String emailCurUser = "email";

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn(emailCurUser);
        when(userRepository.findUserByEmail(emailCurUser)).thenReturn(expecUser);

        User actualUser = userService.getCurAuthUser();
        Optional<User> optionaActuallUser = Optional.of(actualUser);

        assertEquals(expecUser, optionaActuallUser);

    }


    @AfterEach
    void tearDown() {

    }


}
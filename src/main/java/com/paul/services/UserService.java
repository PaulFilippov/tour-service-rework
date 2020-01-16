package com.paul.services;


import com.paul.entities.User;
import com.paul.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

import javax.transaction.Transactional;
import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Set<User> getAllUser() {
        Set<User> allUser = new HashSet();
        //cast from Iterable in Set
        userRepository.findAll().iterator().forEachRemaining(allUser::add);
        return allUser;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("user with email " + email + " not found"));
        return user;
    }

    //возвращает email авторизованого юзера (email вводится в поле username в spring sec)
    public String getCurUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailCurrentAuthUser = auth.getName();
        return emailCurrentAuthUser;
    }

    public void saveUserProf(User user) {
        userRepository.save(user);
    }


    //возвращает из базы сущность авторизованного юзера (ищется по email)
    public User getCurAuthUser() throws NullPointerException {
        String emailCurAuthUser = getCurUserEmail();
        User user = userRepository.findUserByEmail(emailCurAuthUser)
                .orElseThrow(() ->
                        new UsernameNotFoundException("user with email " + emailCurAuthUser + " not found"));
        return user;
    }


}

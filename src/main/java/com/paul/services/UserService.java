package com.paul.services;


import com.paul.entities.User;
import com.paul.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Objects;
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
        Set<User> allUser = new HashSet<>();
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

    //возвращает email авторизованого юзера (email вводится в поле username в spring security)
    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return currentPrincipalName;
//        return emailCurrentAuthUser;
    }

    public void saveUserProfile(User user) {
        userRepository.save(user);
    }

    //возвращает из базы сущность авторизованного юзера (ищется по email)
    public User getCurAuthUser() throws NullPointerException {
        String emailCurAuthUser = getCurrentUserEmail();
        User user = userRepository.findUserByEmail(emailCurAuthUser)
                .orElseThrow(() ->
                        new UsernameNotFoundException("user with email " + emailCurAuthUser + " not found"));
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserService that = (UserService) o;
        return Objects.equals(userRepository, that.userRepository);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRepository);
    }
}

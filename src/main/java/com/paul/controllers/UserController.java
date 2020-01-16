package com.paul.controllers;

import com.paul.entities.User;
import com.paul.repositories.UserRepository;
import com.paul.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    //отображение профиля юзера
    @GetMapping(value = "/profile")
    public String showUserProfile(ModelMap model) {
        User user = userService.getCurAuthUser();
        model.addAttribute("user", user);
        return "profile";
    }

    //обработка изменения профиля
    @PostMapping(value = "/profile")
    public String changeUserProfile(HttpServletRequest request) {
        String firstname = request.getParameter("first_name");
        String lstname = request.getParameter("last_name");
        String strDate = request.getParameter("birthday");
        Date dt = null;
        try {
            SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
            dt = f1.parse(strDate);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        User curInfoUser = userService.getCurAuthUser();
        curInfoUser.setFirst_name(firstname);
        curInfoUser.setLast_name(lstname);
        curInfoUser.setBirthday(dt);
        userService.saveUserProf(curInfoUser);
        return "redirect:/profile";
    }

}


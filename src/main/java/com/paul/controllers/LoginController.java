package com.paul.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    public LoginController() {
    }

    @GetMapping(value = {"/", "/login"})
    public String showLoginPage() {
        return "login";
    }


    @PostMapping(value = "/login")
    public String userAuthorization(Model model) {
        model.addAttribute("user", model);
        return "tours";
    }

}

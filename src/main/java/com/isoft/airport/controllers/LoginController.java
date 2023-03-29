package com.isoft.airport.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) Boolean error,
                                Model model){
        if (error!=null && error)
            model.addAttribute("error","Email or password is incorrect");
        return "login";
    }

    @GetMapping("/logout")
    public String showLogout(HttpServletRequest request, Model model, HttpServletResponse response, Authentication authentication){
        new SecurityContextLogoutHandler().logout(request,response,authentication);
        model.addAttribute("message","You logged out successfully");
        return "login";
    }
}

package com.isoft.airport.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

import static com.isoft.airport.config.SecurityConfig.ROLE_ADMIN;
import static com.isoft.airport.config.SecurityConfig.ROLE_PASSENGER;

@Controller
public class PanelController{
    @GetMapping("/panel")
    public String showPanel(Model model,Authentication authentication){
        String role=new ArrayList<>(authentication.getAuthorities()).get(0).getAuthority();
        model.addAttribute("role",role.substring(role.indexOf('_')+1));
        return "panel";
    }
}

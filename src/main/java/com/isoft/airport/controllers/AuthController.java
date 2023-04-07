package com.isoft.airport.controllers;

import com.isoft.airport.config.SecurityConfig;
import com.isoft.airport.models.FullPassenger;
import com.isoft.airport.models.Passenger;
import com.isoft.airport.models.PassengerDetails;
import com.isoft.airport.models.Role;
import com.isoft.airport.services.PassengerDetailsService;
import com.isoft.airport.services.PassengerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.UUID;

import static com.isoft.airport.config.SecurityConfig.ROLE_PASSENGER;

@Controller
@Transactional
public class AuthController {
    private static final String SIGN_UP = "sign-up";
    private final PassengerDetailsService passengerDetailsService;
    private final PassengerService passengerService;
    private final SecurityConfig.UserPassAuthProvider provider;

    public AuthController(PassengerDetailsService passengerDetailsService,
                          PassengerService passengerService, SecurityConfig.UserPassAuthProvider provider) {
        this.passengerDetailsService = passengerDetailsService;
        this.passengerService = passengerService;
        this.provider = provider;
    }

    @GetMapping("/logout")
    public String showLogout(HttpServletRequest request, Model model, HttpServletResponse response, Authentication authentication) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        model.addAttribute("message", "You logged out successfully");
        return "login";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute(name = "signUpForm") FullPassenger fullPassenger, BindingResult result, String password
            , BCryptPasswordEncoder encoder) {

        if (result.hasErrors())
            return SIGN_UP;

        if (passengerDetailsService.findByEmailAddress(fullPassenger.getEmailAddress().trim()).isPresent()) {
            result.addError(new ObjectError("errors", "Passenger with this email currently exists"));
            return SIGN_UP;

        } else if (passengerDetailsService.findByTelephoneNo(fullPassenger.getTelephoneNo().trim()).isPresent()) {
            result.addError((new ObjectError("errors", "Passenger with this telephone no currently exists")));
            return SIGN_UP;
        }

        Passenger p = new Passenger();
        p.setFirstName(fullPassenger.getFirstName());
        p.setLastName(fullPassenger.getLastName());
        p.setPassportNo(UUID.randomUUID().toString().substring(0, 9));

        PassengerDetails pd = PassengerDetails.builder()
                .birthdate(fullPassenger.getBirthdate())
                .zip(fullPassenger.getZip())
                .city(fullPassenger.getCity())
                .gender(fullPassenger.getGender())
                .country(fullPassenger.getCountry())
                .street(fullPassenger.getStreet())
                .role(new Role(2, ROLE_PASSENGER))
                .password(encoder.encode(password))
                .telephoneNo(fullPassenger.getTelephoneNo())
                .emailAddress(fullPassenger.getEmailAddress())
                .passenger(p)
                .build();

        p.addPassengerDetails(pd);
        passengerService.save(p);

        provider.autoLogin(fullPassenger.getEmailAddress(), password);

        return "redirect:/panel";
    }

}

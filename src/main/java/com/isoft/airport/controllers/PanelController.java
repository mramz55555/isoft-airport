package com.isoft.airport.controllers;

import com.isoft.airport.models.Passenger;
import com.isoft.airport.models.PassengerDetails;
import com.isoft.airport.services.BookingService;
import com.isoft.airport.services.FlightService;
import com.isoft.airport.services.PassengerDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Optional;


@Controller
@RequestMapping("/panel")
public class PanelController {
    private final FlightService flightService;
    private final BookingService bookingService;
    private final PassengerDetailsService passengerDetailsService;

    public PanelController(FlightService flightService, BookingService bookingService, PassengerDetailsService passengerDetailsService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
        this.passengerDetailsService = passengerDetailsService;
    }

    @GetMapping("")
    public String showPanel(Model model, Authentication authentication, HttpSession session) {
        String role = new ArrayList<>(authentication.getAuthorities()).get(0).getAuthority();
        model.addAttribute("role", role.substring(role.indexOf('_') + 1));
        String name = authentication.getName();
        session.setAttribute("name", name.substring(name.lastIndexOf(" ") + 1));
        return "panel";
    }

    @GetMapping("/bookings")
    public String showFlights(Model model, HttpSession session) {
        Optional<PassengerDetails> optionalPassengerDetails;
        Passenger passenger;
        if ((optionalPassengerDetails = passengerDetailsService
                .findByEmailAddress((String) session.getAttribute("name"))).isPresent()) {
            model.addAttribute("bookings", (passenger = optionalPassengerDetails.get().getPassenger()).getBookings());
            model.addAttribute("passengerName", passenger.getFullName());
        }
        return "bookings";
    }

    @GetMapping("/bookings/remove")
    public String removeBooking(@RequestParam(name = "booking_id") long bookingId, HttpSession session) {
        return AdminController.removeBooking0(bookingId, bookingService, session, false);
    }
}

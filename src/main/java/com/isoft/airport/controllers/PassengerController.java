package com.isoft.airport.controllers;

import com.isoft.airport.repositories.FlightScheduleRepository;
import com.isoft.airport.repositories.PassengerDetailsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PassengerController {
    private FlightScheduleRepository flightScheduleRepository;
    private PassengerDetailsRepository passengerDetailsRepository;

    public PassengerController(PassengerDetailsRepository passengerDetailsRepository, FlightScheduleRepository flightScheduleRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
        this.passengerDetailsRepository = passengerDetailsRepository;
    }

    @GetMapping("reserve-flight")
    public String showReservedFlights(@RequestParam String flightno){
//        PassengerDetails passenger=passengerService.findByFlightno(flightno);
        return null;
    }
}

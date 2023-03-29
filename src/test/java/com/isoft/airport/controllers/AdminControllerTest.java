package com.isoft.airport.controllers;

import com.isoft.airport.repositories.BookingRepository;
import com.isoft.airport.repositories.FlightRepository;
import com.isoft.airport.repositories.PassengerDetailsRepository;
import com.isoft.airport.repositories.PassengerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AdminControllerTest {
    @Autowired
    private  AdminController controller;
    @Autowired
    private  PassengerDetailsRepository passengerDetailsRepository;
    @Autowired
    private  PassengerRepository passengerRepository;
    @Autowired
    private  BookingRepository bookingRepository;
    @Autowired
    private  FlightRepository flightRepository;

    @Test
    void deletePassenger() {
        int bookingRepoSize = ((List) bookingRepository.findAll()).size();
        int flightRepoSize = ((List) flightRepository.findAll()).size();
        controller.deletePassenger(4 + "");
        Assertions.assertNotEquals(bookingRepoSize, ((List) bookingRepository.findAll()).size());
        Assertions.assertNotEquals(flightRepoSize, ((List) flightRepository.findAll()).size());
    }
}
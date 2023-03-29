package com.isoft.airport;

import com.isoft.airport.repositories.FlightScheduleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AirportApplicationTests {

    @Autowired
    private FlightScheduleRepository flightScheduleRepository;

    @Test
    void contextLoads() {
//        List<FlightSchedule> flightSchedules=flightScheduleRepository.querySearch(PageRequest.of(0,15)).getContent();
//        List<Airport> list= (List<Airport>) airportRepository.findAll();
//        list.forEach(System.out::println);
//        Airport airport=airportRepository.findById(1).orElse(new Airport());
//        System.out.println(airport.getAirlines());
//        Assertions.assertEquals(1,airport.getAirlines().size());
    }

}

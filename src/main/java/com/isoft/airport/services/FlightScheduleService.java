package com.isoft.airport.services;

import com.isoft.airport.models.FlightSchedule;
import com.isoft.airport.repositories.FlightScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlightScheduleService {
    private final FlightScheduleRepository repository;

    public FlightScheduleService(FlightScheduleRepository repository) {
        this.repository = repository;
    }

    public Optional<FlightSchedule> findByFlightno(String flightno) {
        return repository.findByFlightno(flightno);
    }

}

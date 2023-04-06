package com.isoft.airport.services;

import com.isoft.airport.repositories.AirportReachableRepository;
import org.springframework.stereotype.Service;

@Service
public class AirportReachableService {
    private final AirportReachableRepository repository;

    public AirportReachableService(AirportReachableRepository repository) {
        this.repository = repository;
    }

}

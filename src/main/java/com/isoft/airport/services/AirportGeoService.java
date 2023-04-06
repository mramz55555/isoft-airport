package com.isoft.airport.services;

import com.isoft.airport.repositories.AirportGeoRepository;
import org.springframework.stereotype.Service;

@Service
public class AirportGeoService {
    private AirportGeoRepository repository;

    public AirportGeoService(AirportGeoRepository repository) {
        this.repository = repository;
    }
}

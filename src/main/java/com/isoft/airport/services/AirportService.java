package com.isoft.airport.services;

import com.isoft.airport.models.Airport;
import com.isoft.airport.repositories.AirportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirportService  {
    private final AirportRepository repository;

    public AirportService(AirportRepository repository) {
        this.repository = repository;
    }

    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }


    public Page<Airport> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    public Optional<Airport> findById(long airportId1) {
        return repository.findById(airportId1);
    }
}

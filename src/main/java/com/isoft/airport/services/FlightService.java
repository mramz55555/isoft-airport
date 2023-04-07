package com.isoft.airport.services;

import com.isoft.airport.models.Flight;
import com.isoft.airport.repositories.FlightRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FlightService{
  private final FlightRepository repository;

    public FlightService(FlightRepository repository) {
        this.repository = repository;
    }

    @Query("select f from Flight f " +
            "inner join Airport a on a.airportId =  f.fromAirport.airportId " +
            "inner join Airport a2 on f.toAirport.airportId = a2.airportId " +
            "where date(f.departure) = :departure " +
            "and date(f.arrival) = :arrival " +
            "and a.name = :fromName " +
            "and a2.name = :toName")
    public Page<Flight> querySearch(Pageable pageable, LocalDate departure, LocalDate arrival, String fromName, String toName) {
        return repository.querySearch(pageable, departure, arrival, fromName, toName);
    }
    public Page<Flight> findAll(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

}

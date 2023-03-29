package com.isoft.airport.repositories;

import com.isoft.airport.models.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface FlightRepository extends PagingAndSortingRepository<Flight, Integer> {
    @Query(value = "select f from Flight f "+
            "inner join Airport a on a.airportId =  f.fromAirport.airportId " +
            "inner join Airport a2 on f.toAirport.airportId = a2.airportId " +
            "where date(f.departure) = :departure " +
            "and date(f.arrival) = :arrival " +
            "and a.name = :fromName " +
            "and a2.name = :toName")
    Page<Flight> querySearch(Pageable pageable, LocalDate departure, LocalDate arrival, String fromName, String toName);
}

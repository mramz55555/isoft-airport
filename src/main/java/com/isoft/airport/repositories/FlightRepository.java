package com.isoft.airport.repositories;

import com.isoft.airport.models.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface FlightRepository extends PagingAndSortingRepository<Flight, Long> {
    @Query(nativeQuery = true,value = "select f.flight_id,f.flightno,f.`from`,f.`to`,f.departure,f.airplane_id," +
            "f.arrival,a.airport_id,f.airline_id,f.created_at,f.created_by,f.updated_at,f.updated_by from flight f "+
            "inner join airport a on a.airport_id =  f.`from`" +
            "inner join airport a2 on f.to = a2.airport_id " +
            "where date(f.departure) = :departure " +
            "and date(f.arrival) = :arrival " +
            "and a.name = :fromName " +
            "and a2.name = :toName")
    Page<Flight> querySearch(Pageable pageable, LocalDate departure, LocalDate arrival, String fromName, String toName);
}

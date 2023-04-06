package com.isoft.airport.repositories;

import com.isoft.airport.models.FlightSchedule;
import com.isoft.airport.models.views.FlightScheduleView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FlightScheduleRepository extends PagingAndSortingRepository<FlightSchedule, Long> {
    @Query(value = "select f.fromAirport.name,f.toAirport.name,f.arrival,f.departure from FlightSchedule f " +
            "inner join Airport a on a.airportId =  f.fromAirport.airportId " +
            "inner join Airport a2 on f.toAirport.airportId = a2.airportId " +
            "where date(f.departure) = :departure " +
            "and date(f.arrival) = :arrival " +
            "and a.name = :fromName " +
            "and a2.name = :toName")
    Page<FlightScheduleView> querySearch(Pageable pageable, LocalDate departure, LocalDate arrival, String fromName, String toName);
    Optional<FlightSchedule> findByFlightno(String flightno);
}

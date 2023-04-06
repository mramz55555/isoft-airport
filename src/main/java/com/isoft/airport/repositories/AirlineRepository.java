package com.isoft.airport.repositories;

import com.isoft.airport.models.Airline;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AirlineRepository extends PagingAndSortingRepository<Airline,Long> {
}

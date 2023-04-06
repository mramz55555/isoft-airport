package com.isoft.airport.repositories;

import com.isoft.airport.models.Airport;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AirportRepository extends PagingAndSortingRepository<Airport,Long> {
}

package com.isoft.airport.repositories;

import com.isoft.airport.models.Passenger;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PassengerRepository extends PagingAndSortingRepository<Passenger,Integer> {
}

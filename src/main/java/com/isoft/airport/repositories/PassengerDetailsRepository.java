package com.isoft.airport.repositories;

import com.isoft.airport.models.PassengerDetails;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerDetailsRepository extends PagingAndSortingRepository<PassengerDetails, Integer> {
    PassengerDetails findByEmailAddress(String email);
}

package com.isoft.airport.repositories;

import com.isoft.airport.models.PassengerDetails;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerDetailsRepository extends PagingAndSortingRepository<PassengerDetails, Long> {
    PassengerDetails findByEmailAddress(String email);
    Optional<PassengerDetails> findByEmailAddressEquals(String email);
    Optional<PassengerDetails> findByTelephoneNoEquals(String telephoneNo);
}

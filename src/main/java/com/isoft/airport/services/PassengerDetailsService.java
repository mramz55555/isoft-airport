package com.isoft.airport.services;

import com.isoft.airport.models.PassengerDetails;
import com.isoft.airport.repositories.PassengerDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassengerDetailsService{
    private final PassengerDetailsRepository repository;

    public PassengerDetailsService(PassengerDetailsRepository repository) {
        this.repository = repository;
    }

    public Optional<PassengerDetails> findByEmailAddress(String email){
        return repository.findByEmailAddressEquals(email);
    }

    public Optional<PassengerDetails> findByTelephoneNo(String telephoneNo){
        return repository.findByTelephoneNoEquals(telephoneNo);
    }

    public void deleteById(long passengerId1) {
        repository.deleteById(passengerId1);
    }
}

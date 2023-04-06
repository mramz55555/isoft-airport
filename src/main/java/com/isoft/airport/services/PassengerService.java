package com.isoft.airport.services;

import com.isoft.airport.models.FullPassenger;
import com.isoft.airport.models.Passenger;
import com.isoft.airport.repositories.PassengerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassengerService{
    private final PassengerRepository repository;

    public PassengerService(PassengerRepository repository) {
        this.repository = repository;
    }

    public Page<FullPassenger> findAllFullPassenger(Pageable pageable) {
        return repository.findAllFullPassenger(pageable);
    }

    public <S extends Passenger> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<Passenger> findById(long passengerId1) {
        return repository.findById(passengerId1);
    }

    public void deleteById(long passengerId1) {
        repository.deleteById(passengerId1);
    }

}

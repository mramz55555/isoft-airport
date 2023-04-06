package com.isoft.airport.services;

import com.isoft.airport.models.Booking;
import com.isoft.airport.repositories.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService {
    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public Iterable<Booking> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public Page<Booking> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public <S extends Booking> S save(S entity) {
        return repository.save(entity);
    }

    public <S extends Booking> Iterable<S> saveAll(Iterable<S> entities) {
        return repository.saveAll(entities);
    }

    public Optional<Booking> findById(Long aLong) {
        return repository.findById(aLong);
    }

    public boolean existsById(Long aLong) {
        return repository.existsById(aLong);
    }

    public Iterable<Booking> findAll() {
        return repository.findAll();
    }

    public Iterable<Booking> findAllById(Iterable<Long> longs) {
        return repository.findAllById(longs);
    }

    public long count() {
        return repository.count();
    }

    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

    public void delete(Booking entity) {
        repository.delete(entity);
    }

    public void deleteAllById(Iterable<? extends Long> longs) {
        repository.deleteAllById(longs);
    }

    public void deleteAll(Iterable<? extends Booking> entities) {
        repository.deleteAll(entities);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}

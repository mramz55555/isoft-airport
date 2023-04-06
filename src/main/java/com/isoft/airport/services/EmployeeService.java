package com.isoft.airport.services;

import com.isoft.airport.models.Employee;
import com.isoft.airport.repositories.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Iterable<Employee> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    public Page<Employee> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Employee> findById(Long aLong) {
        return repository.findById(aLong);
    }


    public Iterable<Employee> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long aLong) {
        repository.deleteById(aLong);
    }

}

package com.isoft.airport.repositories;

import com.isoft.airport.models.Employee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee,Long> {
}

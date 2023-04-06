package com.isoft.airport.repositories;

import com.isoft.airport.models.Booking;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookingRepository extends PagingAndSortingRepository<Booking,Long> {
}

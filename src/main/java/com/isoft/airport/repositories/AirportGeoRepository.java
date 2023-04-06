package com.isoft.airport.repositories;

import com.isoft.airport.models.AirportGeo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AirportGeoRepository extends PagingAndSortingRepository<AirportGeo,Long> {
}

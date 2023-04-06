package com.isoft.airport.repositories;

import com.isoft.airport.models.FullPassenger;
import com.isoft.airport.models.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PassengerRepository extends PagingAndSortingRepository<Passenger,Long> {
    @Query("select new com.isoft.airport.models.FullPassenger(p.passengerId,p.passportNo,p.firstName,p.lastName,pd.birthdate,pd.gender," +
            "pd.street,pd.city,pd.zip,pd.country,pd.emailAddress,pd.telephoneNo) from Passenger p inner join PassengerDetails pd on " +
            "p.passengerId = pd.passengerId")
    Page<FullPassenger> findAllFullPassenger(Pageable pageable);
}

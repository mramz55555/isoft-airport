package com.isoft.airport.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "airport")
@Entity
public class Airline extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id")
    private int airlineId;
    private String iata;
    @Column(name = "airlinename")
    private String airlineName;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "base_airport")
    private Airport airport;
    @OneToMany(mappedBy = "airline" ,fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    Set<Flight> flights=new HashSet<>();
    @OneToMany(mappedBy = "airline",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    Set<FlightSchedule> schedules=new HashSet<>();

    public void setAirlineId(int airlineId) {
        this.airlineId = airlineId;
    }

    public void addFlight(Flight flight) {
        Objects.requireNonNull(flight);
        this.flights.add(flight);
        flight.setAirline(this);
    }

    public void addSchedule(FlightSchedule schedule) {
        Objects.requireNonNull(schedule);
        this.schedules.add(schedule);
        schedule.setAirline(this);
    }

    public void setAirport(Airport airport){
        Objects.requireNonNull(airport);
        airport.getAirlines().add(this);
        this.airport=airport;
    }


    public void setIata(String iata) {
        this.iata = iata;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
}

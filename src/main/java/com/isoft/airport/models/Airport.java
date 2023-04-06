package com.isoft.airport.models;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"fromAirport", "toAirport"})
@Entity
@Getter
@Setter
public class Airport extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id")
    private long airportId;
    @OneToMany(mappedBy = "airport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Airline> airlines = new HashSet<>();
    private String iata;
    private String icao;
    private String name;
    @OneToMany(mappedBy = "fromAirport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Flight> fromAirport = new HashSet<>();
    @OneToMany(mappedBy = "toAirport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Flight> toAirport = new HashSet<>();
    @OneToMany(mappedBy = "fromAirport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FlightSchedule> fromSchedule = new HashSet<>();
    @OneToMany(mappedBy = "toAirport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FlightSchedule> toSchedule = new HashSet<>();
    @OneToMany(mappedBy = "airport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AirportGeo> airportGeos = new ArrayList<>();
    @OneToMany(mappedBy = "airport", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<AirportReachable> airportReachables = new HashSet<>();

    public void addAirline(Airline airline) {
        Objects.requireNonNull(airline);
        airlines.add(airline);
        airline.setAirport(this);
    }

    public void addAirportReachable(AirportReachable airportReachable) {
        Objects.requireNonNull(airportReachable);
        airportReachable.setAirport(this);
        this.airportReachables.add(airportReachable);
    }

    public void addAirportGeo(AirportGeo airportGeo) {
        Objects.requireNonNull(airportGeo);
        airportGeo.setAirport(this);
        this.airportGeos.add(airportGeo);
    }
}

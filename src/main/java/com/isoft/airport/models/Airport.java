package com.isoft.airport.models;

import lombok.*;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private int airportId;
    @OneToMany(mappedBy = "airport", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
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
    private Set<AirportGeo> airportGeos = new HashSet<>();
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

    public void setAirportIid(int airportId) {
        this.airportId = airportId;
    }

    public void setIata(String iata) {
        this.iata = iata;
    }

    public void setIcao(String icao) {
        this.icao = icao;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.isoft.airport.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.*;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "flightschedule")
public class FlightSchedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String flightno;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "from")
    private Airport fromAirport;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "to")
    private Airport toAirport;
    private LocalTime departure;
    private LocalTime arrival;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "airline_id")
    private Airline airline;
    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<Flight> flights = new HashSet<>();
    private int monday;
    private int tuesday;
    private int wednesday;
    private int thursday;
    private int friday;
    private int saturday;
    private int sunday;

    public void addFlight(Flight flight) {
        Objects.requireNonNull(flight);
        flight.setSchedule(this);
        this.flights.add(flight);
    }

    public List<String> getFlightDays() {
        List<String> days = new ArrayList<>();
        if (monday == 1) days.add("monday");
        if (tuesday == 1) days.add("tuesday");
        if (wednesday == 1) days.add("wednesday");
        if (thursday == 1) days.add("thursday");
        if (friday == 1) days.add("friday");
        if (saturday == 1) days.add("saturday");
        if (sunday == 1) days.add("sunday");
        return days;
    }
}

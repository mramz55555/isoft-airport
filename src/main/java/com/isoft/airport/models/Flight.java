package com.isoft.airport.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Flight extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_id", insertable = false, updatable = false)
    private long flightId;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "from")
    private Airport fromAirport;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "to")
    private Airport toAirport;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "flightno", insertable = false, updatable = false)
    private FlightSchedule schedule;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "airline_id")
    private Airline airline;
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "airplane_id")
    private Airplane airplane;
    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<FlightLog> logs = new HashSet<>();
    @OneToMany(mappedBy = "flight", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();
    private LocalDate departure;
    private LocalDate arrival;

    public void addBooking(Booking booking) {
        Objects.requireNonNull(booking);
        booking.setFlight(this);
        this.bookings.add(booking);
    }

    public void setSchedule(FlightSchedule schedule) {
        Objects.requireNonNull(schedule);
        this.schedule = schedule;
        schedule.getFlights().add(this);
    }

    public void addLog(FlightLog log) {
        Objects.requireNonNull(log);
        this.logs.add(log);
        log.setFlight(this);
    }
}

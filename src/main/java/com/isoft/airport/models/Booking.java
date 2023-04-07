package com.isoft.airport.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private long bookingId;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "flight_id")
    private Flight flight;
    private String seat;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;
    private double price;

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public void setFlight(Flight flight) {
        Objects.requireNonNull(flight);
        this.flight = flight;
        flight.addBooking(this);
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public void setPassenger(Passenger passenger) {
        Objects.requireNonNull(passenger);
        this.passenger = passenger;
        passenger.addBooking(this);
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Booking(Flight flight, String seat, Passenger passenger, double price) {
        this.flight = flight;
        this.seat = seat;
        this.passenger = passenger;
        this.price = price;
    }
}

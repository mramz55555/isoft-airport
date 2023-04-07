package com.isoft.airport.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Passenger extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private long passengerId;
    @Column(name = "passportno")
    private String passportNo;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @OneToMany(mappedBy = "passenger", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Booking> bookings = new HashSet<>();
    @OneToMany(mappedBy = "passenger", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PassengerDetails> passengerDetails = new HashSet<>();

    public void addBooking(Booking booking) {

        Objects.requireNonNull(booking);
        booking.setPassenger(this);
        this.bookings.add(booking);
    }

    public void addPassengerDetails(PassengerDetails passengerDetails) {
        Objects.requireNonNull(passengerDetails);
        this.passengerDetails.add(passengerDetails);
        passengerDetails.setPassenger(this);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}

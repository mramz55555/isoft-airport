package com.isoft.airport.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class FullPassenger {
    private int passengerId;
    private String passportNo;
    private String firstName;
    private String lastName;
    private Set<Booking> bookings = new HashSet<>();
    private LocalDate birthdate;
    private char gender;
    private String street;
    private String city;
    private int zip;
    private String country;
    private String emailAddress;
    private String telephoneNo;
}

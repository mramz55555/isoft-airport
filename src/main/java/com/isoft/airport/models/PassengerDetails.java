package com.isoft.airport.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "passengerdetails")
public class PassengerDetails extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id",insertable = false,updatable = false)
    private int passenger_id;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "passenger_id",insertable = false,updatable = false)
    private Passenger passenger;
    private LocalDate birthdate;
    private char gender;
    private String street;
    private String city;
    private int zip;
    private String country;
    @Column(name = "emailaddress")
    private String emailAddress;
    @Column(name = "telephoneno")
    private String telephoneNo;
    private String password;
    @ManyToOne(fetch =FetchType.LAZY,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role")
    private Role role;

}

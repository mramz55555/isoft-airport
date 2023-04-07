package com.isoft.airport.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "passengerdetails")
public class PassengerDetails extends BaseEntity {
    @Id
    @Column(name = "passenger_id")
    private long passengerId;
    @MapsId
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "passenger_id",referencedColumnName = "passenger_id")
    private Passenger passenger;
    private LocalDate birthdate;
    private char gender;
    private String street;
    private String city;
    private int zip;
    private String country;
    @Pattern(regexp = "^[\\w\\-\\.]+@([\\w\\-]+\\.)+[\\w\\-]{2,4}$"
            , message = "email address is not valid")
    @Column(name = "emailaddress")
    private String emailAddress;
    @Column(name = "telephoneno")
    private String telephoneNo;
    private String password;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "role")
    private Role role;

}

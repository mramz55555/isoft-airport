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
public class FlightLog extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_log_id")
    private long flightLogId;
    private LocalDate logDate;
    private String user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id")
    private Flight flight;
    @Column(name = "flightno_old")
    private String flightNoOld;
    @Column(name = "flightno_new")
    private String flightNoNew;
    private int fromOld;
    private int toOld;
    private int fromNew;
    private int toNew;
    private LocalDate departureOld;
    private LocalDate arrivalOld;
    private LocalDate departureNew;
    private LocalDate arrivalNew;
    private long airplaneIdOld;
    private long airplaneIdNew;
    private long airlineIdOld;
    private long airlineIdNew;
    private String comment;
}

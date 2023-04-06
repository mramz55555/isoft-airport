package com.isoft.airport.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "airport")
@Getter
@Setter
@Entity
public class AirportReachable extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id",insertable = false,updatable = false)
    private long airportId;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "airport_id",insertable = false,updatable = false)
    private Airport airport;
    private int hops;

    public void setAirportId(long airportId) {
        this.airportId = airportId;
    }

    public void setAirport(Airport airport) {
        Objects.requireNonNull(airport);
        this.airport = airport;
        airport.addAirportReachable(this);
    }

    public void setHope(int hope) {
        this.hops = hope;
    }
}

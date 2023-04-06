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
public class AirportGeo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airport_id",insertable = false,updatable = false)
    private long airportId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "airport_id",insertable = false,updatable = false)
    private Airport airport;
    private String name,city,country,geolocation;
    private double latitude,longitude;

    public void setAirport(Airport airport) {
        Objects.requireNonNull(airport);
        airport.addAirportGeo(this);
        this.airport = airport;
    }

    public void setAirportId(long airportId) {
        this.airportId = airportId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

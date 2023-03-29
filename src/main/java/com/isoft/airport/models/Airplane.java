package com.isoft.airport.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Airplane extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airplane_id")
    private int airplaneId;
    private int capacity;
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "type_id")
    private AirplaneType airplaneType;
    @Column(name = "airline_id")
    private int airlineId;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,mappedBy = "airplane")
    private Set<Flight> flights;

    public void setAirplaneId(int airplaneId) {
        this.airplaneId = airplaneId;
    }

    public void setFlight(Flight flight){
        Objects.requireNonNull(flight);
        flight.setAirplane(this);
        this.flights.add(flight);
    }

    public void setAirplaneType(AirplaneType airplaneType) {
        Objects.requireNonNull(airplaneType);
        airplaneType.getAirplanes().add(this);
        this.airplaneType = airplaneType;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setAirlineId(int airlineId) {
        this.airlineId = airlineId;
    }
}

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
@Entity
@Getter
@Setter
public class AirplaneType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private long typeId;
    private String identifier,description;
    @OneToMany(mappedBy = "airplaneType",fetch = FetchType.LAZY)
    private Set<Airplane> airplanes=new HashSet<>();

    public void addAirplane(Airplane airplane) {
        Objects.requireNonNull(airplane);
        this.airplanes.add(airplane);
        airplane.setAirplaneType(this);
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

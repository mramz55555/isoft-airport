package com.isoft.airport.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@IdClass(WeatherData.WeatherId.class)
public class WeatherData extends BaseEntity {
    @Id
    private LocalDate log_date;
    @Id
    private LocalDate time;
    @Id
    private int station;
    private double temp;
    private double humidity;
    private double airPressure;
    private double wind;
    private int windDirection;
    private Weather weather;

    private static enum Weather {
        NebelSchneefall("Nebel-Schneefall"),
        Schneefall("Schneefall"),
        Regen("Regen"),
        RegenSchneefall("Regen-Schneefall"),
        NebelRegen("Nebel-Regen"),
        NebelRegenGewitter("Nebel-Regen-Gewitter"),
        Gewitter("Gewitter"),Nebel("Nebel"),
        RegenGewitter("Regen-Gewitter");
        private String weather;
        Weather(String weather) {
            this.weather = weather;
        }
    }
    @NoArgsConstructor
    @AllArgsConstructor
    static final class WeatherId implements Serializable {
        private LocalDate log_date;
        private LocalTime time;
        private int station;
    }

}

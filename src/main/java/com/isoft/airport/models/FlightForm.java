package com.isoft.airport.models;

import com.isoft.airport.validators.FlightDates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FlightDates(dateOne = "departureDate",dateTwo = "arrivalDate",message = "dates order are not correct or you chose an invalid date")
public class FlightForm {
    @NotNull(message = "beginning airport must be defined")
    private String from;
    @NotNull(message = "destination airport must be defined")
    private String to;
    @NotNull(message = "departure date airport must be defined")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate departureDate;
    @NotNull(message = "arrival date must be defined")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate arrivalDate;
    private int numberOFAdults, numberOfChildren;
    private ClassType classType;

    @Getter
     enum ClassType {
        ECONOMY("ECONOMY"), BUSINESS("BUSINESS"), FIRST_CLASS("FIRST CLASS");
        private final String type;

        ClassType(String type) {
            this.type = type;
        }
    }
}

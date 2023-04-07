package com.isoft.airport.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class FullPassenger {
    private static final String CONSTRAINT = " should not be blank";

    private long passengerId;
    private String passportNo;
    @NotBlank(message = "First name" + CONSTRAINT)
    private String firstName;
    @NotBlank(message = "Last name" + CONSTRAINT)
    private String lastName;
    private final Set<Booking> bookings = new HashSet<>();
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthdate;
    private char gender;
    @NotBlank(message = "Street" + CONSTRAINT)
    private String street;
    @NotBlank(message = "City" + CONSTRAINT)
    private String city;
    @Min(value = 1, message = "Zip" + CONSTRAINT)
    private int zip;
    @NotBlank(message = "Country " + CONSTRAINT)
//    @Pattern(regexp = "^(?!country).*$",message = "provide a valid country")
    private String country;
    @Pattern(regexp = "^[\\w\\-\\.]+@([\\w\\-]+\\.)+[\\w\\-]{2,4}$", message = "email address is not valid")
    private String emailAddress;
    @NotBlank(message = "mobile number" + CONSTRAINT)
    @Pattern(regexp = "^$|[0-9]+", message = "Please provide a valid telephone number")
    private String telephoneNo;

}

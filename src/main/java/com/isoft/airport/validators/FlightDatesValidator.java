package com.isoft.airport.validators;

import com.isoft.airport.models.FlightForm;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FlightDatesValidator implements ConstraintValidator<FlightDates, FlightForm> {
    private FlightDates flightDates;
    private String message;
    private final LocalDate hypotheticalDate = LocalDate.of(2014, 5, 26);

    @Override
    public void initialize(FlightDates constraintAnnotation) {
        flightDates = constraintAnnotation;
        try {
            FlightForm.class.getDeclaredField(flightDates.dateOne());
            FlightForm.class.getDeclaredField(flightDates.dateTwo());
            message = constraintAnnotation.message();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isValid(@ModelAttribute("flightForm") FlightForm value, ConstraintValidatorContext context) {
        return !value.getDepartureDate().isAfter(value.getArrivalDate())
                && !value.getDepartureDate().isBefore(hypotheticalDate)
                && !value.getArrivalDate().isBefore(hypotheticalDate);
    }
}

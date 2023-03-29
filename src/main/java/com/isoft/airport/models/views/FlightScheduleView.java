package com.isoft.airport.models.views;

import com.isoft.airport.models.Airport;

import java.time.LocalDate;
import java.time.LocalDate;

public interface FlightScheduleView {
    Airport getFromAirport();
    Airport getToAirport();
    LocalDate getArrival();
    LocalDate getDeparture();
}

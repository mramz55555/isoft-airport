package com.isoft.airport.controllers;

import com.isoft.airport.models.*;
import com.isoft.airport.services.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.Set;

import static com.isoft.airport.controllers.PublicController.parseParam;

@Controller
@Transactional
@RequestMapping("/admin")
public class AdminController {
    private static final String PASSENGERS = "passengers";
    private static final String PASSENGER_ID = "passengerId";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String PAGE__NUMBER = "page-number";
    private static final String TOTAL_PAGES = "totalPages";
    private static final String BOOKINGS = "bookings";
    private final int nOfRecords = 15;

    private final PassengerDetailsService passengerDetailsService;
    private final PassengerService passengerService;
    private final BookingService bookingService;
    private final FlightScheduleService flightScheduleService;
    private final FlightService flightService;
    private final EmployeeService employeeService;
    private final AirportService airportService;
    private final AirportReachableService airportReachableService;


    public AdminController(JdbcTemplate template, PassengerDetailsService passengerDetailsService,
                           PassengerService passengerService, BookingService bookingService,
                           FlightScheduleService flightScheduleService, FlightService flightService, EmployeeService employeeService, AirportService airportService, AirportReachableService airportReachableService) {

        this.passengerDetailsService = passengerDetailsService;
        this.passengerService = passengerService;
        this.bookingService = bookingService;
        this.flightScheduleService = flightScheduleService;
        this.flightService = flightService;
        this.employeeService = employeeService;
        this.airportService = airportService;
        this.airportReachableService = airportReachableService;
    }

    @GetMapping("/" + PASSENGERS)
    public String showPASSENGERS(Model model, @RequestParam(required = false, name = PAGE__NUMBER) String pageNumber, HttpSession session) {
        int pageNumber1 = PublicController.parseParam(pageNumber, true);

        Page<FullPassenger> fullPassengerPage = passengerService.findAllFullPassenger(PageRequest.of(pageNumber1, nOfRecords));
        model.addAttribute(PASSENGERS, fullPassengerPage);
        model.addAttribute(PAGE_NUMBER, pageNumber1);
        if (session.getAttribute(TOTAL_PAGES) == null)
            session.setAttribute(TOTAL_PAGES, fullPassengerPage.getTotalPages());
        model.addAttribute(TOTAL_PAGES, session.getAttribute(TOTAL_PAGES));
        return PASSENGERS;
    }

    @GetMapping("/" + PASSENGERS + "/remove")
    @Transactional
    public String deletePassenger(@RequestParam(required = false, name = "passenger_id") String passengerId) {
        long passengerId1 = parseParam(passengerId, false);

        Optional<Passenger> optionalPassenger = passengerService.findById(passengerId1);
        if (optionalPassenger.isPresent()) {
            Set<Booking> bookingsSet = optionalPassenger.get().getBookings();
            bookingsSet.forEach(b -> b.getFlight().getBookings().remove(b));
            bookingService.deleteAll(bookingsSet);

            passengerDetailsService.deleteById(passengerId1);
            passengerService.deleteById(passengerId1);
        }
        return "redirect:/admin/" + PASSENGERS;
    }

    @GetMapping("/passenger/bookings")
    public String showPassengerBookings(@RequestParam(value = "passenger_id", required = false) String passengerId,
                                        @RequestParam(value = PAGE__NUMBER, required = false) String pageNumber,
                                        HttpSession session,
                                        Model model) {

        long passengerId1 = parseParam(passengerId, false);
        int pageNumber1 = parseParam(pageNumber, true);

        Optional<Passenger> optionalPassenger = passengerService.findById(passengerId1);

        if (optionalPassenger.isPresent()) {

            Passenger passenger = optionalPassenger.get();

            model.addAttribute("passengerName", passenger.getFirstName() + " " + passenger.getLastName());
            model.addAttribute(PAGE_NUMBER, pageNumber1);

            if (session.getAttribute(PASSENGER_ID) == null)
                session.setAttribute(PASSENGER_ID, passenger.getPassengerId());
            else
                model.addAttribute(PASSENGER_ID, session.getAttribute(PASSENGER_ID));

            Set<Booking> bookings = passenger.getBookings();

            model.addAttribute(TOTAL_PAGES, Math.ceil(bookings.size() / nOfRecords));
            model.addAttribute(BOOKINGS, bookings
                    .stream()
                    .skip(nOfRecords * (pageNumber1 - 1))
                    .limit(nOfRecords)
                    .toList());

            return BOOKINGS;
        }
        throw new IllegalArgumentException("Invalid parameter");
    }

    @GetMapping("/passenger/bookings/remove")
    @Transactional
    public String removeBooking(@RequestParam(name = "booking_id") long bookingId, HttpSession session) {
        Optional<Booking> optionalBooking = bookingService.findById(bookingId);
        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            booking.getFlight().getBookings().remove(booking);
            booking.getPassenger().getBookings().remove(booking);
            bookingService.deleteById(bookingId);
            return "redirect:/admin/passenger/bookings?passenger_id=" + session.getAttribute("passengerId");
        }
        throw new IllegalArgumentException("Booking not found");
    }


    @GetMapping("/employees")
    @Transactional
    public String showEmployees(Model model, @RequestParam(value = PAGE__NUMBER, required = false) String pageNumber) {
        long pageNumber1 = parseParam(pageNumber, true);
        Page<Employee> page = employeeService.findAll(PageRequest.of((int) pageNumber1,
                nOfRecords, Sort.by("firstname", "lastname")));

        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("employees", page.getContent());
        model.addAttribute("pageNumber", pageNumber1);
        return "employees";
    }

    @GetMapping("/employee/remove")
    @Transactional
    public String removeEmployee(Model model, @RequestParam(value = "employee_id", required = false) String employeeId) {
        long employeeId1 = parseParam(employeeId, false);
        Optional<Employee> optionalEmployee = employeeService.findById(employeeId1);

        if (optionalEmployee.isPresent()) {
            employeeService.deleteById(employeeId1);
            return "redirect:/admin/employees";
        }
        throw new IllegalArgumentException("Employee not found");
    }


    @GetMapping("/flights")
    public String showFlights(@RequestParam(value = PAGE__NUMBER, required = false) String pageNumber,
                              @RequestParam(required = false) String flightno,
                              Model model) {
        if (flightno != null)
            PublicController.showFlightSchedule0(flightno, flightScheduleService, model);

        int pageNumber1 = parseParam(pageNumber, true);
        Page<Flight> flightPage = flightService.findAll(PageRequest.of(
                pageNumber1 - 1, nOfRecords, Sort.by("departure").descending()));

        model.addAttribute("totalPages", flightPage.getTotalPages());
        model.addAttribute("pageNumber", pageNumber1);
        model.addAttribute("flights", flightPage.getContent());

        return "flights";
    }

    @GetMapping("/airports")
    public String showAirports(@RequestParam(value = PAGE__NUMBER, required = false) String pageNumber,
                               Model model) {
        int pageNumber1 = parseParam(pageNumber, true);

        Page<Airport> airportPage = airportService.findAll(PageRequest.of(
                pageNumber1 - 1, nOfRecords, Sort.by("name").descending()));

        model.addAttribute("totalPages", airportPage.getTotalPages());
        model.addAttribute("pageNumber", pageNumber1);
        model.addAttribute("airports", airportPage.getContent());

        return "airports";
    }


    @GetMapping("/airport/remove")
    @Transactional
    public String removeAirport(Model model, @RequestParam(value = "airport_id", required = false) String airportId) {

        long airportId1 = parseParam(airportId, false);
        Optional<Airport> optionalAirport = airportService.findById(airportId1);

        if (optionalAirport.isPresent()) {
            airportService.deleteById(airportId1);
            return "redirect:/admin/airports";
        }
        throw new IllegalArgumentException("Airport not found");
    }
}


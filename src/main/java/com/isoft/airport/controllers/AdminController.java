package com.isoft.airport.controllers;

import com.isoft.airport.models.Booking;
import com.isoft.airport.models.FullPassenger;
import com.isoft.airport.models.Passenger;
import com.isoft.airport.repositories.BookingRepository;
import com.isoft.airport.repositories.FlightRepository;
import com.isoft.airport.repositories.PassengerDetailsRepository;
import com.isoft.airport.repositories.PassengerRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.isoft.airport.controllers.HomeController.parsePageNumber;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final String PAGE_NAME = "passengers";
    private static final String HEADERS = "headers";
    private static final String RESULTS_DATA = "resultsData";

    private final int nOfRecords = 15;

    private final JdbcTemplate template;
    private final PassengerDetailsRepository passengerDetailsRepository;
    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public AdminController(JdbcTemplate template, PassengerDetailsRepository passengerDetailsRepository, PassengerRepository passengerRepository, BookingRepository bookingRepository, FlightRepository flightRepository) {
        this.template = template;
        this.passengerDetailsRepository = passengerDetailsRepository;
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }


    @GetMapping("/passengers")
    public String showPassengers(Model model, @RequestParam(required = false, name = "page-number") String pageNumber) {
        int pageNumber1 = HomeController.parsePageNumber(pageNumber, true);

        List<FullPassenger> fullPassengerList = template.query("select p.passenger_id, passportNo,firstname,lastname,birthdate,gender,street,city,zip,country," +
                "emailaddress,telephoneno from passenger inner join passengerdetails p on passenger.passenger_id = p.passenger_id limit "
                + nOfRecords + " offset " + (pageNumber1 - 1) * nOfRecords, (ResultSet rs, int rn) -> {
            FullPassenger fullPassenger = new FullPassenger();
            fullPassenger.setPassengerId(rs.getInt(1));
            fullPassenger.setPassportNo(rs.getString(2));
            fullPassenger.setFirstName(rs.getString(3));
            fullPassenger.setLastName(rs.getString(4));
            fullPassenger.setBirthdate(rs.getObject(5, LocalDate.class));
            fullPassenger.setGender(rs.getString(6).charAt(0));
            fullPassenger.setStreet(rs.getString(7));
            fullPassenger.setCity(rs.getString(8));
            fullPassenger.setZip(rs.getInt(9));
            fullPassenger.setCountry(rs.getString(10));
            fullPassenger.setEmailAddress(rs.getString(11));
            fullPassenger.setTelephoneNo(rs.getString(12));
            return fullPassenger;
        });
        model.addAttribute("passengers", fullPassengerList);

        model.addAttribute("pageNumber", pageNumber1);
        model.addAttribute("totalPages", Math.ceil((double) template.query(
                "select row_number() over() as rn from passenger order by rn desc limit 1;", (rs, rn) -> rs.getInt(1)).get(0) / 15));
        return PAGE_NAME;
    }

    @GetMapping("passengers/remove")
    @Transactional
    public String deletePassenger(@RequestParam(required = false, name = "passenger_id") String passengerId) {
        int passengerId1 = parsePageNumber(passengerId, false);

        Optional<Passenger> optionalPassenger = passengerRepository.findById(passengerId1);
        if (optionalPassenger.isPresent()) {
            Set<Booking> bookingsSet = optionalPassenger.get().getBookings();
            bookingsSet.forEach(b -> b.getFlight().getBookings().remove(b));
            bookingRepository.deleteAll(bookingsSet);

            passengerDetailsRepository.deleteById(passengerId1);
            passengerRepository.deleteById(passengerId1);
        }
        return "redirect:/admin/passengers";
    }

    @GetMapping("/passenger/bookings")
    public String showPassengerBookings(@RequestParam(value = "passenger_id", required = false) String passengerId,
                                        @RequestParam(value = "pageNumber", required = false) String pageNumber,
                                        HttpSession session,
                                        Model model) {
        int passengerId1 = parsePageNumber(passengerId, false);
        int pageNumber1 = parsePageNumber(pageNumber, true);

        Optional<Passenger> optionalPassenger = passengerRepository.findById(passengerId1);

        if (optionalPassenger.isPresent()) {

            Passenger passenger = optionalPassenger.get();

            model.addAttribute("passengerName", passenger.getFirstName() + " " + passenger.getLastName());
            model.addAttribute("pageNumber", pageNumber1);

            if (session.getAttribute("passengerId") == null)
                session.setAttribute("passengerId", passenger.getPassengerId());
            else
                model.addAttribute("passengerId", session.getAttribute("passengerId"));

            Set<Booking> bookings;
            if (session.getAttribute("bookings") == null) {
                bookings = passenger.getBookings();
                session.setAttribute("bookings", bookings);
            }
            else
                bookings = (Set<Booking>) session.getAttribute("bookings");

            model.addAttribute("totalPages", Math.ceil(bookings.size() / nOfRecords));
            model.addAttribute("bookings", bookings
                    .stream()
                    .skip(nOfRecords * (pageNumber1 - 1))
                    .limit(nOfRecords)
                    .toList());

            return "bookings";
        }
        throw new IllegalArgumentException("Invalid parameter");
    }

//    @GetMapping("/bookings")
//    public String showBookings(){
//
//    }
}

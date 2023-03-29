package com.isoft.airport.controllers;

import com.isoft.airport.models.Flight;
import com.isoft.airport.models.FlightForm;
import com.isoft.airport.models.FlightSchedule;
import com.isoft.airport.repositories.FlightRepository;
import com.isoft.airport.repositories.FlightScheduleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Controller
public class HomeController {
    private final FlightScheduleRepository flightScheduleRepository;
    private final FlightRepository flightRepository;
    @Value("${flights.isoft.page.size}")
    public int pageSize;

    public HomeController(FlightScheduleRepository flightScheduleRepository, FlightRepository flightRepository) {
        this.flightScheduleRepository = flightScheduleRepository;
        this.flightRepository = flightRepository;
    }

    @GetMapping({"/", "/home"})
    public String showHome(Model model, @RequestParam(name = "page-number", required = false) String pageNumber,
                           @RequestParam(required = false, name = "error") boolean error,
                           HttpSession session) {

        if (error) model.addAttribute("errors", session.getAttribute("errors"));

        model.addAttribute("flightForm", new FlightForm());
        int pageNumber1 = parsePageNumber(pageNumber,true);

        Page<Flight> flightPage = flightRepository
                .findAll(PageRequest.of(pageNumber1 - 1, pageSize, Sort.by("departure")));

        model.addAllAttributes(Map.of(
                "totalPages", flightPage.getTotalPages(),
                "flights", flightPage.getContent(),
                "pageNumber", pageNumber1));

        return "home";
    }

    @GetMapping("/search")
    public String searchFlightWithFormValidation(@RequestParam(name = "page-number", required = false) String pageNumber,
                                                 @Valid @ModelAttribute(name = "flightForm") FlightForm form, BindingResult result,
                                                 Model model, HttpSession session) {
        if (result.hasErrors()) {
            session.setAttribute("errors", result.getAllErrors().stream().map(e -> e.getDefaultMessage()).toList());
            return "redirect:home?error=true";
        }

        session.setAttribute("flightForm", form);
        showNthPageOfResult(pageNumber, model, session);
        return "flights";
    }

    @GetMapping("/search/flights")
    public String showNthPageOfResult(@RequestParam(name = "page-number") String pageNumber, Model model, HttpSession session) {

        int pageNumber1 = parsePageNumber(pageNumber,true);
        FlightForm form1 = (FlightForm) session.getAttribute("flightForm");
        Page<Flight> flightPage = flightRepository.querySearch(PageRequest.of(
                        pageNumber1 - 1, pageSize * 2, Sort.by("departure")),
                form1.getDepartureDate(),form1.getArrivalDate(),form1.getFrom(),form1.getTo());

        model.addAllAttributes(Map.of("totalPages", flightPage.getTotalPages(),
                "pageNumber", pageNumber1,
                "flights", flightPage.getContent()));

        return "flights";
    }

    @GetMapping("/search/flights/schedule/{flight_schedule_no}")
    public String showFlightSchedule(@PathVariable(name = "flight_schedule_no") String flightScheduleNo, Model model) {
        Optional<FlightSchedule> optionalFlightSchedule = flightScheduleRepository.findByFlightno(flightScheduleNo);
        if (optionalFlightSchedule.isPresent()) {
            model.addAttribute("flightSchedule", optionalFlightSchedule.get());
            return "flights";
        } else
            throw new IllegalArgumentException("Flight Schedule with this flight number not found!");
    }

    public static int parsePageNumber(String param,boolean isPageNumber) {
        int param1;
        try {
            if (isPageNumber) param1 = Integer.parseInt(param==null? (param="1") : param);
            param1=Integer.parseInt(param);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid param");
        }
        return param1;
    }
}

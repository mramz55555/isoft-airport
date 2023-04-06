package com.isoft.airport.controllers;

import com.isoft.airport.models.Flight;
import com.isoft.airport.models.FlightForm;
import com.isoft.airport.models.FlightSchedule;
import com.isoft.airport.models.FullPassenger;
import com.isoft.airport.services.FlightScheduleService;
import com.isoft.airport.services.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/public")
@Transactional
public class PublicController {
    private final FlightScheduleService flightScheduleService;
    private final FlightService flightService;
    @Value("${flights.isoft.page.size}")
    public int pageSize;

    public PublicController(FlightScheduleService flightScheduleService, FlightService flightService) {
        this.flightScheduleService = flightScheduleService;
        this.flightService = flightService;
    }

    @GetMapping({"/", "/home"})
    public String showHome(Model model, @RequestParam(name = "page-number", required = false) String pageNumber,
                           @RequestParam(required = false, name = "error") boolean error,
                           HttpSession session) {

        if (error) model.addAttribute("errors", session.getAttribute("errors"));

        model.addAttribute("flightForm", new FlightForm());
        int pageNumber1 = parseParam(pageNumber, true);

        Page<Flight> flightPage = flightService
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

        int pageNumber1 = parseParam(pageNumber, true);
        FlightForm form1 = (FlightForm) session.getAttribute("flightForm");
        Page<Flight> flightPage = flightService.querySearch(PageRequest.of(
                        pageNumber1 - 1, pageSize * 2, Sort.by("departure")),
                form1.getDepartureDate(), form1.getArrivalDate(), form1.getFrom(), form1.getTo());

        model.addAllAttributes(Map.of("totalPages", flightPage.getTotalPages(),
                "pageNumber", pageNumber1,
                "flights", flightPage.getContent()));

        return "flights";
    }


    @GetMapping("/search/flights/schedule/{flight_schedule_no}")
    public String showFlightSchedule(@PathVariable(name = "flight_schedule_no") String flightScheduleNo, Model model) {
        return showFlightSchedule0(flightScheduleNo, flightScheduleService, model);
    }


    public static String showFlightSchedule0(@PathVariable(name = "flight_schedule_no") String flightScheduleNo, FlightScheduleService fr, Model model) {
        Optional<FlightSchedule> optionalFlightSchedule = fr.findByFlightno(flightScheduleNo);
        if (optionalFlightSchedule.isPresent()) {
            model.addAttribute("flightSchedule", optionalFlightSchedule.get());
            return "flights";
        } else
            throw new IllegalArgumentException("Flight Schedule with this flight number not found!");
    }


    @GetMapping("/about")
    public String showAbout(){
        return "about";
    }

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(required = false) Boolean error,
                                Model model){
        if (error!=null && error)
            model.addAttribute("error","Email or password is incorrect");
        return "login";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model){
        model.addAttribute("signUpForm",new FullPassenger());
        return "sign-up";
    }



    public static int parseParam(String param, boolean isPageNumber) {
        int param1;
        try {
            if (isPageNumber)
                param1 = (param == null) ? 1 : Integer.parseInt(param);
            else param1 = (param == null) ? param1 = 0 : Integer.parseInt(param);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid param");
        }
        return param1;
    }
}

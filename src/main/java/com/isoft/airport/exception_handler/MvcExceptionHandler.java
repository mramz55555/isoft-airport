package com.isoft.airport.exception_handler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class MvcExceptionHandler {

    @ExceptionHandler({Exception.class})
    public String showErrorPage(Model model,Exception e){
        model.addAttribute("errorMessage",e.getMessage());
        e.printStackTrace();
        return "error";
    }
}

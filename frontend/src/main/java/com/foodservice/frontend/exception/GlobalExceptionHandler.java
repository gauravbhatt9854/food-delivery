package com.foodservice.frontend.exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServerResponseException.class)
    public String handleServerResponseException(ServerResponseException serverResponseException, Model model) {

        model.addAttribute("errorTitle", "API response error");
        model.addAttribute("errorMessage", serverResponseException.getMessage());

        return "pages/error";
    }

    @ExceptionHandler(UnauthorizedException.class)
    public String handleUnauthorizedException(UnauthorizedException ex, Model model) {

        model.addAttribute("errorTitle", "401 code");
        model.addAttribute("errorMessage", ex.getMessage());

        return "pages/error";
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    public String handleMissingRequestCookieException(MissingRequestCookieException missingRequestCookieException, Model model) {

        model.addAttribute("errorTitle", "401 code");
        model.addAttribute("errorMessage", "Please login to access this page");

        return "pages/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(Exception exception, Model model) {

        model.addAttribute("errorMessage", exception.getMessage());

        return "pages/error";
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public String handleInvalidCredentialsException(InvalidCredentialsException invalidCredentialsException, Model model) {

        model.addAttribute("status", "error");
        model.addAttribute("message", invalidCredentialsException.getMessage());

        return "pages/login";
    }

}

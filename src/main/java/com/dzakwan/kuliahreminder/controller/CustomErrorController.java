package com.dzakwan.kuliahreminder.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<String> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object messageObj = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object exceptionObj = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        // ✅ Tambahkan log ke console
        System.out.println("==== ERROR HANDLED ====");
        System.out.println("Status Code  : " + status);
        System.out.println("Message      : " + messageObj);
        System.out.println("Exception    : " + exceptionObj);
        System.out.println("========================");

        String message = "Unknown error";
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            httpStatus = HttpStatus.valueOf(statusCode);
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                message = "404 - Not Found";
            } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                message = "405 - Method Not Allowed. Gunakan POST untuk endpoint ini.";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                message = "500 - Internal Server Error";
            } else {
                message = statusCode + " - Unexpected Error";
            }
        }

        return new ResponseEntity<>(message, httpStatus);
    }
}


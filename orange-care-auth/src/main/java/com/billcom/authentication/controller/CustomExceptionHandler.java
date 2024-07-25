package com.billcom.authentication.controller;

import com.billcom.authentication.domains.AuthenticationResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Log4j2
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AuthenticationResponse> handleInvokeClientException(BadCredentialsException exception) {
        log.error(exception);
        AuthenticationResponse baseWSResponse = AuthenticationResponse.builder()
                .isSuccessful(false)
                .comment("Authentication Failed")
                .build();
        log.error(exception);
        exception.printStackTrace();
        return new ResponseEntity<>(baseWSResponse, HttpStatus.BAD_REQUEST);
    }
}


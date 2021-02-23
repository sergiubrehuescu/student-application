package com.example.school.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalHandlerException {

    Logger logger = LoggerFactory.getLogger(GlobalHandlerException.class);

    @ExceptionHandler(ResourceNotFoundException.class)

    public ResponseEntity<ExceptionResponse> beerNotFound(ResourceNotFoundException exception){
        ExceptionResponse er = new ExceptionResponse(); //primeste erorile care vin din exceptie
        er.setMessage(exception.getMessage());
        er.setErrorCode("Not FOUND");
        er.setTime(LocalDateTime.now());

        logger.error(exception.getMessage(),exception);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ExceptionResponse> beerToReplaceNotFound(IndexOutOfBoundsException exception){
        ExceptionResponse er = new ExceptionResponse(); //primeste erorile care vin din exceptie
        er.setMessage(exception.getMessage());
        er.setErrorCode("Not FOUND");
        er.setTime(LocalDateTime.now());

        logger.error(exception.getMessage(),exception);
        return new ResponseEntity<>(er, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<ExceptionResponse> nullPointerExcep(NullPointerException exception){
        ExceptionResponse er = new ExceptionResponse();
        er.setMessage(exception.getMessage());
        er.setErrorCode("NOT FOUND");
        er.setTime(LocalDateTime.now());

        logger.error(exception.getMessage(),exception);
        return new ResponseEntity<>(er,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionResponse> handleGeneralErrors(Throwable exception) {
        ExceptionResponse er = new ExceptionResponse();
        er.setMessage(exception.getMessage());
        er.setTime(LocalDateTime.now());
        er.setErrorCode("Error");

        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
    }

}

package com.execom.pomodoro.controller;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler({ EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(
            Exception ex, WebRequest request){
        return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleInternalServerErrorException(
            Exception ex, WebRequest request){
        return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    }
}
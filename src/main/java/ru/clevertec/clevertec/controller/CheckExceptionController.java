package ru.clevertec.clevertec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.clevertec.exception.CheckException;

@ControllerAdvice
public class CheckExceptionController {

    @ExceptionHandler(CheckException.class)
    public ResponseEntity<String> handleException(CheckException e) {

        return new ResponseEntity<>(e.getMessage(),e.getHttpStatus());
    }

}

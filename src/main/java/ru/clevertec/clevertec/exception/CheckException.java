package ru.clevertec.clevertec.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class CheckException extends Exception{
    private final String message;
    private final HttpStatus httpStatus;
}
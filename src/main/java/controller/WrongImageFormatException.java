package main.java.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongImageFormatException extends RuntimeException {
    public WrongImageFormatException() {
    }

    public WrongImageFormatException(String message) {
        super(message);
    }
}

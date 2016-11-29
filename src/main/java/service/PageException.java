package main.java.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PageException extends RuntimeException{
    public PageException() {
    }

    public PageException(String message) {
        super(message);
    }
}

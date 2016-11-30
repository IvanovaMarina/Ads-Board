package main.java.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdvertNotFoundException extends RuntimeException{
    public AdvertNotFoundException() {
    }

    public AdvertNotFoundException(String message) {
        super(message);
    }
}

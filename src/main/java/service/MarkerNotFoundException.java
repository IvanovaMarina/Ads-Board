package main.java.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MarkerNotFoundException extends RuntimeException {
    public MarkerNotFoundException() {
    }

    public MarkerNotFoundException(String message) {
        super(message);
    }
}

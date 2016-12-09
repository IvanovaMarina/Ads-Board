package main.java.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MarkerAlreadyExistingException extends RuntimeException {
    public MarkerAlreadyExistingException() {
    }

    public MarkerAlreadyExistingException(String message) {
        super(message);
    }
}

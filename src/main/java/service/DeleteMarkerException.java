package main.java.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class DeleteMarkerException extends RuntimeException {
    public DeleteMarkerException() {
    }

    public DeleteMarkerException(String message) {
        super(message);
    }
}

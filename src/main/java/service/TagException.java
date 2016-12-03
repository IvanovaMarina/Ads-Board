package main.java.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TagException extends RuntimeException{

    public TagException() {
    }

    public TagException(String message) {
        super(message);
    }
}

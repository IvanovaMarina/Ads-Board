package main.java.service;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistingException extends RuntimeException {

    public UserAlreadyExistingException() {
        super();
    }

    public UserAlreadyExistingException(String message) {
        super(message);
    }
}

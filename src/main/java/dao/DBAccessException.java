package main.java.dao;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DBAccessException extends RuntimeException{

    public DBAccessException(String message) {
        super(message);
    }

    public DBAccessException() {
    }
}

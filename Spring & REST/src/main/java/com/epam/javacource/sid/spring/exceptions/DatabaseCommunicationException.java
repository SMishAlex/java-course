package com.epam.javacource.sid.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseCommunicationException extends RuntimeException {

    public DatabaseCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}

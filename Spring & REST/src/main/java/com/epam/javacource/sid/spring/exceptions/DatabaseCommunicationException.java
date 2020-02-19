package com.epam.javacource.sid.spring.exceptions;

public class DatabaseCommunicationException extends RuntimeException {

    public DatabaseCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}

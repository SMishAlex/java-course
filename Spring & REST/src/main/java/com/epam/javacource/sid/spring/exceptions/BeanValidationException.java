package com.epam.javacource.sid.spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BeanValidationException extends RuntimeException {

    public BeanValidationException(String message) {
        super(message);
    }
}

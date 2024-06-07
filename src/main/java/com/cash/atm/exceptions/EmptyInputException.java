package com.cash.atm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmptyInputException extends RuntimeException {
    private String resourceName;

    public EmptyInputException(String resourceName) {
        super(String.format("Input cannot be read a one quantity",resourceName));
        this.resourceName = resourceName;
    }
}
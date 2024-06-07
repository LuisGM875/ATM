package com.cash.atm.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND )
public class ExceedsATMMoney extends RuntimeException {
    private String resourceName;

    public ExceedsATMMoney(String resourceName){
        super(String.format("This amount cannot be returned in coins: ", resourceName));
        this.resourceName=resourceName;
    }
}

package com.cash.atm.exceptions;

import com.cash.atm.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ExceedsATMMoney.class})
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ExceedsATMMoney e, WebRequest webRequest){

        ApiResponse apiResponse = new ApiResponse(e.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<ApiResponse> handleEmptyInputException(EmptyInputException i, WebRequest webRequest){
        ApiResponse apiResponse = new ApiResponse(i.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
}

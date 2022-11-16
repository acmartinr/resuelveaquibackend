package com.ecommerce.ecommerce.handlers;

import com.ecommerce.ecommerce.payload.response.StandarizedApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleNoContentExcpeption(Exception ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Validation error", "1024",ex.getMessage());
        return new ResponseEntity(response, HttpStatus.PARTIAL_CONTENT);
    }
/*
    //Validator for params field
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Missing input fields", "1025",ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
    //Validator for number fields
    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> httpMessageNotReadable(NumberFormatException ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Invalid field type", "1026",ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

 */
}

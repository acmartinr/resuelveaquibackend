package com.ecommerce.ecommerce.handlers;

import com.ecommerce.ecommerce.payload.response.StandarizedApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@RestControllerAdvice
public class ApiExceptionHandler {

    //Validator for params field
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleMissingParams(MethodArgumentNotValidException  ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Campos necesarios del rquest vacios o inexistentes", "1025",ex.getMessage());
        return new ResponseEntity(response, HttpStatus.PARTIAL_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleNoContentExcpeption(Exception ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Validation error", "1024",ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
/*

    //Validator for number fields
    @ExceptionHandler(value = NumberFormatException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> httpMessageNotReadable(NumberFormatException ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Invalid field type", "1026",ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

 */
}

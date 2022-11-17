package com.ecommerce.ecommerce.handlers;

import com.ecommerce.ecommerce.common.payload.exception.BussinesRuleException;
import com.ecommerce.ecommerce.common.payload.exception.CreditCardException;
import com.ecommerce.ecommerce.common.payload.response.StandarizedApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    @ExceptionHandler(BussinesRuleException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handleNoContentExcpeption(BussinesRuleException ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Producto insuficiente", "1026",ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CreditCardException.class)
    public ResponseEntity<StandarizedApiExceptionResponse> handlebadCreditCard(CreditCardException ex) {
        StandarizedApiExceptionResponse response = new StandarizedApiExceptionResponse("Error al realizar pago", "1027",ex.getMessage());
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

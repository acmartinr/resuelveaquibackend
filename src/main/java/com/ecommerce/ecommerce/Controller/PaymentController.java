package com.ecommerce.ecommerce.Security.Controller;

import com.ecommerce.ecommerce.Models.PaymentRequest;
import com.ecommerce.ecommerce.Services.PaymentService;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@RestController
@RequestMapping(path = "/api/payment")
public class PaymentController {

    @Autowired
    PaymentService service;

   /* @PostMapping
    public ResponseEntity<String> completePayment(@RequestBody PaymentRequest request) throws StripeException {
        String chargeId= service.charge(request);
        return chargeId!=null? new ResponseEntity<String>(chargeId,HttpStatus.OK):
                new ResponseEntity<String>("Please check the credit card details entered",HttpStatus.BAD_REQUEST);
    }*/

    @PostMapping("/paymentintent")
    public ResponseEntity<String> payment(@RequestBody PaymentRequest paymentRequest) throws StripeException {
        PaymentIntent paymentIntent = service.paymentIntent(paymentRequest);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirm(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = service.confirm(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = service.cancel(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @ExceptionHandler
    public String handleError(StripeException ex) {
        return ex.getMessage();
    }
}

package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.payload.response.PaymentResponse;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class PaymentProcessorTest {
    private PymentGateway pymentGateway;
    PaymentProcessor paymentProcessor;

    @BeforeEach
    public void setup() {
        pymentGateway = Mockito.mock(PymentGateway.class);
        paymentProcessor = new PaymentProcessor(pymentGateway);
    }

    @Test
    public void payment_is_correct() {
        Mockito.when(pymentGateway.requestPayment(Mockito.any())).thenReturn(new PaymentResponse(PaymentResponse.PaymentStatus.OK, "FF232242"));
        assertTrue(paymentProcessor.makePayment(100));
    }

    @Test
    public void payment_is_wrong() {
        Mockito.when(pymentGateway.requestPayment(Mockito.any())).thenReturn(new PaymentResponse(PaymentResponse.PaymentStatus.ERROR, ""));
        assertFalse(paymentProcessor.makePayment(100));
    }

    @Test
    public void payment_chargeid_is_empty() {
        Mockito.when(pymentGateway.requestPayment(Mockito.any())).thenReturn(new PaymentResponse(PaymentResponse.PaymentStatus.OK, ""));
        assertFalse(paymentProcessor.makePayment(100));
    }
}
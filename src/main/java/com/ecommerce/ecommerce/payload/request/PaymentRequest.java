package com.ecommerce.ecommerce.payload.request;

import com.ecommerce.ecommerce.Models.Token;

import java.util.Objects;

public class PaymentRequest {
    private String description;
    private int amount;
    private String currency;
    private String stripeEmail;
    private Token token;

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getStripeEmail() {
        return stripeEmail;
    }
    public void setStripeEmail(String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }
    public Token getToken() {
        return token;
    }
    public void setToken(Token stripeToken) {
        this.token = stripeToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentRequest paymentRequest = (PaymentRequest) o;
        return description == paymentRequest.description && amount == paymentRequest.amount && Objects.equals(currency, paymentRequest.currency) && Objects.equals(stripeEmail, paymentRequest.stripeEmail) && Objects.equals(token, paymentRequest.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, amount, currency, stripeEmail, token);
    }
}

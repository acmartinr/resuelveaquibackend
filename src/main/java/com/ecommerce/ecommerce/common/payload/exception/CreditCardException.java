package com.ecommerce.ecommerce.common.payload.exception;

public class CreditCardException extends Exception{
    private long id;
    private String code;

    public CreditCardException(long id, String code,String message) {
        super(message);
        this.id = id;
        this.code = code;
    }
    public CreditCardException(String code,String message) {
        super(message);
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
package com.ecommerce.ecommerce.Models;

import javax.persistence.*;

@Entity
@Table(name="paymentapikey")
public class PaymentApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    private String publicKey;
    private String secretKey;

    private boolean production;

    public PaymentApiKey() {
        super();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isProduction() {
        return production;
    }

    public void setProduction(boolean production) {
        this.production = production;
    }

}

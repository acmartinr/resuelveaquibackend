package com.ecommerce.ecommerce.Services;


import com.ecommerce.ecommerce.Models.PaymentApiKey;
import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Repository.PaymentApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentApiKeyService {

    @Autowired
    private PaymentApiKeyRepository paymentApiKey;

    @PreAuthorize("hasRole('ADMIN')")
    public PaymentApiKey create(PaymentApiKey producto) {
        return paymentApiKey.save(producto);
    }

    public List<PaymentApiKey> getAllPaymentApiKeys() {
        return paymentApiKey.findAll();
    }

    public String findProdKey() {
        if(paymentApiKey.findFirstByProduction(true).get() != null){
            return paymentApiKey.findFirstByProduction(true).get().getSecretKey();
        }else{
            return "";
        }
    }
    public String findDevKey() {
        if(paymentApiKey.findFirstByProduction(false).get() != null){
            return paymentApiKey.findFirstByProduction(false).get().getSecretKey();
        }else{
            return "";
        }
    }
}

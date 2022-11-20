package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.Category;
import com.ecommerce.ecommerce.Services.PaymentApiKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/paymentapi")
public class PaymentApiKeyController {
    @Autowired
    private PaymentApiKeyService paymentApiKeyService;

    @CrossOrigin
    @GetMapping("/prodkey")
    private ResponseEntity<String> prodKey(){
       return ResponseEntity.ok(paymentApiKeyService.findProdKey());
    }

    @CrossOrigin
    @GetMapping("/devkey")
    private ResponseEntity<String> devKey() {
        return ResponseEntity.ok(paymentApiKeyService.findDevKey());
    }
}

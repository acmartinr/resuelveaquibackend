package com.ecommerce.ecommerce.Security.Controller;

import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Services.ProductSoldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/productsSolds")
public class ProductSoldController {

    @Autowired
    ProductSoldService productSoldService;

    @GetMapping("/")
    public ResponseEntity<List<ProductSold>> getAllProducts() {
        List<ProductSold> list = productSoldService.getAllProductsSolds();
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value="/createProductSold")
    private ResponseEntity<ProductSold> create(@RequestBody ProductSold productSold) throws IOException {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(productSoldService.create(productSold));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping(value = "get/{id}")
    private ResponseEntity<Optional<ProductSold>> buscar(@PathVariable("id") Long id){
        return ResponseEntity.ok(productSoldService.findByID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ProductSold productSold){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productSoldService.update(id,productSold));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable ("id") Long id){
        if(productSoldService.findByID(id)==null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
            productSoldService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}

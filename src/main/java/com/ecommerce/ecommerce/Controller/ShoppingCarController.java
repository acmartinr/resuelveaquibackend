package com.ecommerce.ecommerce.Security.Controller;

import com.ecommerce.ecommerce.Models.ProductSold;
import com.ecommerce.ecommerce.Models.ShoppingCar;
import com.ecommerce.ecommerce.Services.ShoppingCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/shoppingCar")
public class ShoppingCarController {

    @Autowired
    ShoppingCarService shoppingCarService;

    @GetMapping("/")
    public ResponseEntity<List<ShoppingCar>> getAllProducts() {
        List<ShoppingCar> list = shoppingCarService.getAllShoppingCars();
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value="/createShoppingCar")
    private ResponseEntity<ShoppingCar> create(@RequestBody ShoppingCar shoppingCar) throws IOException {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(shoppingCarService.create(shoppingCar));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @GetMapping(value = "get/{id}")
    private ResponseEntity<Optional<ShoppingCar>> buscar(@PathVariable("id") Long id){
        return ResponseEntity.ok(shoppingCarService.findByID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ShoppingCar shoppingCar){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(shoppingCarService.update(id,shoppingCar));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable ("id") Long id){
        if(shoppingCarService.findByID(id)==null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
            shoppingCarService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}

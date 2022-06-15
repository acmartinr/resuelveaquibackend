package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Models.Sale;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.SalesRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(path = "/api/sales")
public class SalesController {
    @Autowired
    SalesRepository salesRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/")
    public ResponseEntity<List<Sale>> showSales() {
        List<Sale> sales = salesRepository.findAll();
        return ResponseEntity.ok(sales);
    }

    @PostMapping(value="/create_sale")
    public ResponseEntity<String> createSale(@RequestPart Producto[] productos, @RequestPart long id) throws IOException {
        Sale sale=new Sale();
        Set<Producto> products=new HashSet<>(Arrays.asList(productos));
        sale.setProducts(products);
        sale.setUserShopping(userRepository.findById(id).get());
        salesRepository.save(sale);
        return ResponseEntity.ok("Venta realizada correctamente");
    }

    @GetMapping(value="/getAll_sale")
    public ResponseEntity<List<Sale>> getAllSale() throws IOException {
        return ResponseEntity.ok(salesRepository.findAll());
    }
}

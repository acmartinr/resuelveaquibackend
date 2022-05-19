package com.ecommerce.ecommerce.ProductoController;


import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductoRest {

    @Autowired
    private ProductoService productoService;


    private ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        Producto temp=productoService.create(producto);

        try {
            return ResponseEntity.created(new URI("/api/producto"+temp.getId())).body(temp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
    @GetMapping("/")
    public ResponseEntity<String> index() {
        // placeRepository.save(new Place(999999999999L,"(-12.2,22.22)","Place for fun weekend",20.0f,"Sarao Bar Playa","La Habana","19 entre D y E ","La Habana",100,0,true,"bar"));
        return ResponseEntity.ok("Hello");
    }
    @GetMapping("/products/all")
    public ResponseEntity<List<Producto>> getAllProducts() {
        List<Producto> le = productoService.getAllProducts();
        System.out.println(le.size());
        return ResponseEntity.ok(le);
    }

    @GetMapping(value = "get/{id}")
    private ResponseEntity<Optional<Producto>> buscar(@PathVariable ("id") Long id){
        return ResponseEntity.ok(productoService.findByID(id));
    }


    @GetMapping
    private ResponseEntity<List<Producto>> listar(){
        return ResponseEntity.ok(productoService.getAllProducts());
    }

    @DeleteMapping
    private ResponseEntity<Void> eliminar(@RequestBody Producto producto){
        productoService.delete(producto);
        return ResponseEntity.ok().build();
    }


}

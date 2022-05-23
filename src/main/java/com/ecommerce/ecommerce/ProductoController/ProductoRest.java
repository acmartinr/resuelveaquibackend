package com.ecommerce.ecommerce.ProductoController;


import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductoRest {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/create")
    private ResponseEntity<Producto> create(/*RequestParam("archivo") MultipartFile archivo,*/@RequestBody Producto producto) throws IOException {
       /* String ruta="C://imagen/img";
        int index=archivo.getOriginalFilename().indexOf(".");
        String namefile="";
        namefile="."+archivo.getOriginalFilename().substring(index+1);
        String nombrefoto= Calendar.getInstance().getTimeInMillis()+namefile;
        Path rutaAbsoluta= Paths.get(ruta+"//"+ nombrefoto);
        Files.write(rutaAbsoluta, archivo.getBytes());*/
        Producto temp=productoService.create(producto);

        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.create(producto));
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

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Producto producto){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.update(id,producto));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /*public ResponseEntity<?> delete(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(productoService.delete(id));
    }*/

}

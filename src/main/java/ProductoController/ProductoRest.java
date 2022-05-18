package ProductoController;


import Models.Producto;
import Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/producto/")
public class ProductoRest {

    @Autowired
    private ProductoService productoService;

    @PostMapping
    private ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        Producto temp=productoService.create(producto);

        try {
            return ResponseEntity.created(new URI("/api/producto"+temp.getId())).body(temp);
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

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

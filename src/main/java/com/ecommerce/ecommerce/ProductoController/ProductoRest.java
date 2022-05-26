package com.ecommerce.ecommerce.ProductoController;


import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Services.ProductoService;
import com.ecommerce.ecommerce.Utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    private static final String  devUrl = "http://localhost:3000";

    @Autowired
    private ProductoService productoService;

    @CrossOrigin(origins = devUrl)
    @GetMapping("/create")
    public String home(Model model){
        Producto producto=new Producto();
        model.addAttribute("producto", producto);
        return "home";
    }


    @CrossOrigin(origins = devUrl)
    @RequestMapping(value="/create", method=RequestMethod.POST,consumes={MediaType.MULTIPART_FORM_DATA_VALUE},
            produces=MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Producto> create(@RequestPart("image") MultipartFile archivo,@RequestPart Producto producto) throws IOException {


        int index=archivo.getOriginalFilename().indexOf(".");
        /*String extension;
        extension="."+archivo.getOriginalFilename().substring(index+1);*/
        String nombreFoto= Calendar.getInstance().getTimeInMillis()+"";
        FileUploadUtil.saveFile("product-images",nombreFoto,archivo);
        producto.setImg(nombreFoto);


        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.create(producto));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    /*@GetMapping("/")
    public ResponseEntity<String> index() {
        // placeRepository.save(new Place(999999999999L,"(-12.2,22.22)","Place for fun weekend",20.0f,"Sarao Bar Playa","La Habana","19 entre D y E ","La Habana",100,0,true,"bar"));
        return ResponseEntity.ok("Hello");
    }*/

    @CrossOrigin(origins = devUrl)
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


    @CrossOrigin(origins = devUrl)
    @GetMapping("/")
    private ResponseEntity<List<Producto>> list(){
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

   @DeleteMapping("/delete/{id}")
   public ResponseEntity<Object> delete(@PathVariable ("id") Long id){
        if(productoService.findByID(id)==null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
        productoService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}

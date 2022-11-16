package com.ecommerce.ecommerce.Controller;


import com.ecommerce.ecommerce.Models.Producto;
import com.ecommerce.ecommerce.Services.ProductoService;
import com.ecommerce.ecommerce.Utils.FileUploadUtil;
import com.ecommerce.ecommerce.Utils.ThumbnailCreateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;


@Api(tags = "Product Controller End Point")
@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductoService productoService;

    @CrossOrigin
    @RequestMapping(value="/create", method=RequestMethod.POST,consumes={MediaType.MULTIPART_FORM_DATA_VALUE},
            produces=MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Producto> create(@RequestPart("image") MultipartFile[] archivos,@RequestPart Producto producto) throws IOException {
        String a="";
        for (MultipartFile archivo:archivos){
        int index=archivo.getOriginalFilename().indexOf(".");
        String extension;
        extension="."+archivo.getOriginalFilename().substring(index+1);
        String nombreFoto= Calendar.getInstance().getTimeInMillis()+extension;
        FileUploadUtil.saveFile("product-images",nombreFoto,archivo);
        String absolute= Paths.get("product-images").toFile().getAbsolutePath()+File.separator+nombreFoto;
        ThumbnailCreateUtil.thumbCreate(absolute);
        a=a.concat(nombreFoto+",").trim();
        }
        a=a.substring(0,a.length()-1);
        producto.setImg(a);
        producto.setThumb(a);
        producto.setDate(LocalDateTime.now());
        System.out.println("ddddddddddd");
        System.out.println(productoService);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.create(producto));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    /*@GetMapping("/")
    public ResponseEntity<String> index() {
        // placeRepository.save(new Place(999999999999L,"(-12.2,22.22)","Place for fun weekend",20.0f,"Sarao Bar Playa","La Habana","19 entre D y E ","La Habana",100,0,true,"bar"));
        return ResponseEntity.ok("Hello");
    }*/

    @ApiOperation(value = "Return allproducts budled into response", notes = "Return 204 if not data founded")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "There are not super heroes"),
            @ApiResponse(code = 500, message = "Internal error")
    })
    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<List<Producto>> getAllProducts() {
        System.out.println(productoService);
        List<Producto> le = productoService.getAllProducts();
        return ResponseEntity.ok(le);
    }

    @CrossOrigin
    @GetMapping(value = "get/{id}")
    private ResponseEntity<?> buscar(@PathVariable ("id") Long id){
        return ResponseEntity.ok(productoService.findByID(id).get());
    }



    /*@GetMapping("/all_products")
    public ResponseEntity<Page<Producto>> list(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size){
        String order="id";
        boolean asc=true;
        Page<Producto> products = productoService.pagination(PageRequest.of(page, size, Sort.by(order)));
        if(!asc)
            products = productoService.pagination(PageRequest.of(page, size, Sort.by(order).descending()));
        return new ResponseEntity<Page<Producto>>(products, HttpStatus.OK);
    }*/

    @CrossOrigin
    @PutMapping("update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Producto producto){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productoService.update(id,producto));
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CrossOrigin
    @DeleteMapping("/delete/{id}")
   public ResponseEntity<Object> delete(@PathVariable ("id") Long id){
        if(productoService.findByID(id)==null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
        productoService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @GetMapping("/all_productos")
    public ResponseEntity<Page<Producto>> pagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String order,
            @RequestParam(defaultValue = "true") boolean asc
    )
    {
        Page<Producto> products = productoService.pagination(PageRequest.of(page, size, Sort.by(order)));
        if(!asc)
            products = productoService.pagination(PageRequest.of(page, size, Sort.by(order).descending()));
        return new ResponseEntity<Page<Producto>>(products, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Producto>> search(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String order,
            @RequestParam(defaultValue = "true") boolean asc,
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "") String type
    )
    {
        if(!name.isEmpty()||!type.isEmpty()){
        name=name.toUpperCase();
        type=type.toUpperCase();
        Page<Producto> products = productoService.paginationSearch(name,type,PageRequest.of(page, size, Sort.by(order)));
        if(!asc)
            products = productoService.paginationSearch(name,type, PageRequest.of(page, size, Sort.by(order).descending()));
        return new ResponseEntity<Page<Producto>>(products, HttpStatus.OK);
        }
            Page<Producto> products = productoService.pagination(PageRequest.of(page, size, Sort.by(order)));
            return new ResponseEntity<Page<Producto>>(products, HttpStatus.OK);
    }
}



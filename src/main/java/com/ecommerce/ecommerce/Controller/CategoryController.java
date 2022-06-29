package com.ecommerce.ecommerce.Security.Controller;


import com.ecommerce.ecommerce.Models.Category;
import com.ecommerce.ecommerce.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private static final String  devUrl = "http://localhost:3000";

    @Autowired
    private CategoryService categoryService;

    @CrossOrigin
    @GetMapping("/categories")
    private ResponseEntity<List<Category>> list(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    @CrossOrigin
    @RequestMapping(value="/createCat")
    private ResponseEntity<Category> create( @RequestBody Category category) throws IOException {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.create(category));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @CrossOrigin
    @GetMapping(value = "getCat/{id}")
    private ResponseEntity<Optional<Category>> buscar(@PathVariable("id") Long id){
        return ResponseEntity.ok(categoryService.findByID(id));
    }

    @CrossOrigin
    @PutMapping("/updateCat/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Category category){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoryService.update(id,category));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CrossOrigin
    @DeleteMapping("/deleteCat/{id}")
    public ResponseEntity<Object> delete(@PathVariable ("id") Long id){
        if(categoryService.findByID(id)==null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
            categoryService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}

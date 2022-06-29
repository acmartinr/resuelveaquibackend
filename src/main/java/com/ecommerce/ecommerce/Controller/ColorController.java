package com.ecommerce.ecommerce.Security.Controller;


import com.ecommerce.ecommerce.Models.Color;
import com.ecommerce.ecommerce.Services.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ColorController {

    @Autowired
    private ColorService colorService;

    @CrossOrigin
    @GetMapping("/colors")
    private ResponseEntity<List<Color>> list(){
        return ResponseEntity.ok(colorService.getAllColors());
    }


    @CrossOrigin
    @RequestMapping(value="/createColor")
    private ResponseEntity<Color> create( @RequestBody Color color) throws IOException {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(colorService.create(color));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @CrossOrigin
    @GetMapping(value = "getColor/{id}")
    private ResponseEntity<Optional<Color>> buscar(@PathVariable("id") Long id){
        return ResponseEntity.ok(colorService.findByID(id));
    }

    @CrossOrigin
    @PutMapping("/updateColor/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Color color){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(colorService.update(id,color));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CrossOrigin
    @DeleteMapping("/deleteColor/{id}")
    public ResponseEntity<Object> delete(@PathVariable ("id") Long id){
        if(colorService.findByID(id)==null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
            colorService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}

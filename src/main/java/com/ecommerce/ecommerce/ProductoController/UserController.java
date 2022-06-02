package com.ecommerce.ecommerce.ProductoController;

import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    private static final String  devUrl = "http://localhost:3000";

    @Autowired
    UserService userService;

    @CrossOrigin
    @GetMapping("/users")
    private ResponseEntity<List<User>> list(){
        return ResponseEntity.ok(userService.getAllUsers());
    }


    @CrossOrigin
    @RequestMapping(value="/createUser")
    private ResponseEntity<User> create( @RequestBody User user) throws IOException {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.create(user));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @CrossOrigin
    @GetMapping(value = "getUser/{id}")
    private ResponseEntity<Optional<User>> buscar(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.findByID(id));
    }

    @CrossOrigin
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody User user){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.update(id,user));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CrossOrigin
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<Object> delete(@PathVariable ("id") Long id){
        if(userService.findByID(id)==null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
            userService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }
}

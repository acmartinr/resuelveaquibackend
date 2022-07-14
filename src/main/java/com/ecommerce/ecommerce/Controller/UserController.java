package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.ChangePasswordDTO;
import com.ecommerce.ecommerce.Models.Mensaje;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final String  devUrl = "http://localhost:3000";

    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @CrossOrigin
    @GetMapping("/all")
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
    @GetMapping(value = "/{id}")
    private ResponseEntity<Optional<User>> buscar(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.findByID(id));
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody User user){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.update(id,user));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable ("id") Long id){
        if(userService.findByID(id)==null)
            return ResponseEntity.ok(Boolean.FALSE);
        else
            userService.delete(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO dto, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
        if(!dto.getPassword().equals(dto.getConfirmPassword()))
            return new ResponseEntity(new Mensaje("Las contraseñas no coinciden"), HttpStatus.BAD_REQUEST);
        Optional<User> usuarioOpt = userService.getByTokenPassword(dto.getTokenPassword());
        if(!usuarioOpt.isPresent())
            return new ResponseEntity(new Mensaje("No existe ningún usuario con esas credenciales"), HttpStatus.NOT_FOUND);
        if(ChronoUnit.MINUTES.between(usuarioOpt.get().getTimeToken(), LocalDateTime.now())>30)
            return new ResponseEntity(new Mensaje("Ha superado los 30 minutos dados para el cambio de contraseña"), HttpStatus.BAD_REQUEST);
        User user = usuarioOpt.get();
        String newPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(newPassword);
        user.setToken(null);
        user.setTimeToken(null);
        userService.save(user);
        return new ResponseEntity(new Mensaje("Contraseña actualizada"), HttpStatus.OK);
    }
}

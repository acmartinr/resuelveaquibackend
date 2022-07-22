package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Models.ChangePasswordDTO;
import com.ecommerce.ecommerce.Models.EmailValuesDTO;
import com.ecommerce.ecommerce.Models.Mensaje;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Services.EmailService;
import com.ecommerce.ecommerce.Services.EmailServiceImpl;
import com.ecommerce.ecommerce.Services.ShoppingCarService;
import com.ecommerce.ecommerce.Services.UserService;
import com.nylas.RequestFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final String  devUrl = "http://localhost:3000";


    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;

    private static final String subject = "Contraseña reseteada";

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
            return new ResponseEntity(new Mensaje("Es posible que hayas escrito mal tu token"), HttpStatus.NOT_FOUND);
        if(ChronoUnit.MINUTES.between(usuarioOpt.get().getTimeToken(), LocalDateTime.now())>30) {
            usuarioOpt.get().setToken(null);
            usuarioOpt.get().setTimeToken(null);
            return new ResponseEntity(new Mensaje("Ha superado los 30 minutos dados para el cambio de contraseña"), HttpStatus.BAD_REQUEST);
        }
            User user = usuarioOpt.get();
        String newPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(newPassword);
        user.setToken(null);
        user.setTimeToken(null);
        userService.update(user.getId(),user);
        return new ResponseEntity(new Mensaje("Contraseña actualizada"), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody EmailValuesDTO dto) throws RequestFailedException, IOException {
        Optional<User> userOpt = userService.getByEmail(dto.getMailTo());
        if(!userOpt.isPresent())
            return new ResponseEntity(new Mensaje("No existe ningún usuario con esas credenciales"), HttpStatus.NOT_FOUND);
        UUID uuid = UUID.randomUUID();
        String resetPassword = uuid.toString();
        User user = userOpt.get();
        String newPassword = passwordEncoder.encode(resetPassword);
        user.setPassword(newPassword);
        userService.update(user.getId(),user);
        emailService.sendEmail(dto.getMailTo(),"Esta es su nueva contraseña: "+resetPassword,subject);
        return new ResponseEntity(new Mensaje("Contraseña actualizada"), HttpStatus.OK);
    }

}

package com.ecommerce.ecommerce.Security.Controller;

import com.ecommerce.ecommerce.Models.ChangePasswordDTO;
import com.ecommerce.ecommerce.Models.EmailValuesDTO;
import com.ecommerce.ecommerce.Models.Mensaje;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.Services.EmailService;
import com.ecommerce.ecommerce.Services.UserService;
import com.nylas.RequestFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/email-password")
@CrossOrigin
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    //@Value("${spring.mail.username}")
    //private String mailFrom;

    private static final String subject = "Cambio de Contraseña";
    private static final String emailMsg = "http://localhost:8080/email-password/change-password";



    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto) throws RequestFailedException, IOException {
       /* if (!userRepository.existsByEmail(dto.getMailTo())){
            return new ResponseEntity(new Mensaje("No existe ningún usuario con esas credenciales"+dto.getMailTo()), HttpStatus.NOT_FOUND);
        }*/
        Optional<User> userOpt = userService.getByEmail(dto.getMailTo());
        if(!userOpt.isPresent())
            return new ResponseEntity(new Mensaje("No existe ningún usuario con esas credenciales"), HttpStatus.NOT_FOUND);
        User user = userOpt.get();
        dto.setMailTo(user.getEmail());
        UUID uuid = UUID.randomUUID();
        String tokenPassword = uuid.toString();
        dto.setTokenPassword(tokenPassword);
        user.setToken(tokenPassword);
        userService.save(user);
        emailService.sendEmail(dto.getMailTo(),"Copie el siguiente token: "+tokenPassword+" y peguelo" +
                " en el siguiente link " +emailMsg,subject);
        return new ResponseEntity(new Mensaje("Te hemos enviado un correo"), HttpStatus.OK);
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
        User user = usuarioOpt.get();
        String newPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(newPassword);
        user.setToken(null);
        userService.save(user);
        return new ResponseEntity(new Mensaje("Contraseña actualizada"), HttpStatus.OK);
    }

}

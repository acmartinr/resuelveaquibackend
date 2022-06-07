package com.ecommerce.ecommerce.Controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.Security.jwt.JwtUtils;
import com.ecommerce.ecommerce.Security.services.UserDetailsImpl;
import com.ecommerce.ecommerce.Services.EmailService;
import com.ecommerce.ecommerce.Services.UserService;
import com.ecommerce.ecommerce.payload.request.LoginRequest;
import com.ecommerce.ecommerce.payload.request.SignupRequest;
import com.ecommerce.ecommerce.payload.response.JwtResponse;
import com.ecommerce.ecommerce.payload.response.MessageResponse;
import com.nylas.RequestFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserService userService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        System.out.println(signUpRequest.getEmail());
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }


        User userCreated = userService.signUp(signUpRequest);
        userService.create(userCreated);

        // Create new user's account
        /*
        User userCreated = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);

         */

        EmailService emailService  = new EmailService();
        try{
            emailService.sendEmail(signUpRequest.getEmail(),"Hola bienvenido a resuelveAqui","Querido usuario");
        }catch (IOException e){
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
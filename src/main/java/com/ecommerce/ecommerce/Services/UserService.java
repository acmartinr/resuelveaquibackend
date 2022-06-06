package com.ecommerce.ecommerce.Services;

import com.ecommerce.ecommerce.Models.SignUp;
import com.ecommerce.ecommerce.Models.User;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.payload.request.SignupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User update(Long id, User user){
        Optional<User> entity=userRepository.findById(id);
        User p=entity.get();
        p=userRepository.save(user);
        return p;
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

    public Optional<User> findByID(Long id){
        return userRepository.findById(id);
    }

    public User signUp(SignupRequest user) {
        String encodedPassword= passwordEncoder.encode(CharBuffer.wrap(user.getPassword()));
        return new User(user.getFirstName(),user.getLastName(),user.getUsername(), user.getEmail(),encodedPassword);
    }
}


package com.se.kientruc.nhom74.user.controller;

import com.se.kientruc.nhom74.user.entity.User;
import com.se.kientruc.nhom74.user.repository.UserRepository;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    final String password = "FoodStore74";

    @PostMapping("/create")
    public String create(@RequestBody User user){
        List<User> us = userRepository.checkEmail(user.getEmail());
        if(!us.isEmpty()){
            return "Email already in use";
        }
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
        String encrypted= encryptor.encrypt(user.getPassword());
        user.setPassword(encrypted);
        userRepository.create(user.getEmail(), user.getAddress(), user.getName(), user.getPassword());
        return "Success";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        List<User> us = userRepository.checkEmail(user.getEmail());
        if(us.isEmpty()){
            return "Invalid email";
        }
        for(User u : us){
            StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
            encryptor.setPassword(password);
            String decrypted = encryptor.decrypt(u.getPassword());
            u.setPassword(decrypted);
            if(u.getPassword().equals(user.getPassword())){
                return "Login success";
            }
        }
        return "Incorrect password";
    }

    @GetMapping("/detail/{email}")
    public User selectall(@PathVariable("email") String email){
        return userRepository.checkEmail(email).get(0);
    }

//    @GetMapping("/circuitbreakerdemo")
//    @CircuitBreaker(name = "login")
//    public String circuitbreakerdemo(){
//        return null;
//    }

}

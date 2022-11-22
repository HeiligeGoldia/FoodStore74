package com.se.kientruc.nhom74.user.controller;

import com.se.kientruc.nhom74.user.entity.User;
import com.se.kientruc.nhom74.user.repository.UserRepository;
import com.se.kientruc.nhom74.user.service.SlowService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private final SlowService slowService;

    public UserController(SlowService slowService) {
        this.slowService = slowService;
    }

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

    @GetMapping("/timelimiterdemo")
    @TimeLimiter(name = "login")
    public CompletableFuture<String> timelimiterdemo(){
        return CompletableFuture.supplyAsync(slowService::slowMethod);
    }

    @ExceptionHandler({TimeoutException.class})
    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    public String handleTimeoutException() {
        return "User service is full and does not permit further calls";
    }

}

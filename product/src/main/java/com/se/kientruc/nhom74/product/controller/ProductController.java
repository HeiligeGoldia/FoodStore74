package com.se.kientruc.nhom74.product.controller;

import com.se.kientruc.nhom74.product.entity.Product;
import com.se.kientruc.nhom74.product.repository.ProductRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    JedisPool jedisPool = new JedisPool("127.0.0.1");
    Jedis jedis = jedisPool.getResource();

    @GetMapping("/all")
    public List<Product> getAll(){
        return productRepository.geAll();
    }

    @GetMapping("/detail/{pid}")
    public Product detail(@PathVariable("pid") String pid){
        return productRepository.getById(pid);
    }

    @GetMapping("/name/{name}")
    public Product getByName(@PathVariable("name") String name){
        return productRepository.getByName(name).get(0);
    }

    @GetMapping("/category/{category}")
    public List<Product> getByCategory(@PathVariable("category") String category){
        return productRepository.getByCategory(category);
    }

    @PostMapping("/addToCart")
    public String addToCart(@RequestBody Product product){
        jedis.sadd("cart", String.valueOf(product.getPid()));
        return "Added to cart";
    }

    @GetMapping("/getFromCart")
    public List<Product> getFromCart(){
        List<Product> cart = new ArrayList<>();
        Set<String> cart_items = jedis.smembers("cart");
        for(String id : cart_items){
            cart.add(productRepository.getById(id));
        }
        return cart;
    }

    @DeleteMapping("/removerAllFromCart")
    public String removerAllFromCart(){
        jedis.del("cart");
        return "Done";
    }
}
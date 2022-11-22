package com.se.kientruc.nhom74.favorite.controller;

import com.se.kientruc.nhom74.favorite.entity.Favorite;
import com.se.kientruc.nhom74.favorite.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Autowired
    FavoriteRepository favoriteRepository;

    @GetMapping("/userFavorite/{email}")
    public List<Favorite> getUserFavorite(@PathVariable("email") String email){
        return favoriteRepository.getUserFavorite(email);
    }

}

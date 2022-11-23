package com.se.kientruc.nhom74.favorite.repository;

import com.se.kientruc.nhom74.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends CrudRepository<Favorite, String> {

    @Query(value = "select * from favorite where email = :email", nativeQuery = true)
    public List<Favorite> getUserFavorite(@Param(value = "email") String email);

}

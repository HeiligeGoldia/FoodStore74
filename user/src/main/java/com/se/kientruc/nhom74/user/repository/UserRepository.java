package com.se.kientruc.nhom74.user.repository;

import com.se.kientruc.nhom74.user.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    @Modifying
    @Transactional
    @Query(value = "insert into user_data values (:email, :address, :name, :password)", nativeQuery = true)
    public int create(@Param(value = "email") String email, @Param(value = "address") String address, @Param(value = "name") String name, @Param(value = "password") String password);

    @Query(value = "select * from user_data where email = :email", nativeQuery = true)
    public List<User> checkEmail(@Param(value = "email") String email);

}

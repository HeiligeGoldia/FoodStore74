package com.se.kientruc.nhom74.product.repository;

import com.se.kientruc.nhom74.product.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query(value = "select * from product", nativeQuery = true)
    public List<Product> geAll();

    @Query(value = "select * from product where name = :name", nativeQuery = true)
    public List<Product> getByName(@Param(value = "name") String name);

    @Query(value = "select * from product where category = :category", nativeQuery = true)
    public List<Product> getByCategory(@Param(value = "category") String category);

    @Query(value = "select * from product where product_id = :pid", nativeQuery = true)
    public Product getById(@Param(value = "pid") String pid);

}

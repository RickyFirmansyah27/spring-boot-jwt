package com.myapp.model.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.myapp.model.Entity.Product;

public interface ProductRepo extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE (:id IS NULL OR p.id = :id) " +
            "AND (:name IS NULL OR p.name LIKE %:name%) " +
            "AND (:description IS NULL OR p.description LIKE %:description%) " +
            "AND (:price IS NULL OR p.price = :price)")
    public List<Product> filteredProduct(@Param("id") Long id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("price") Integer price,
            Pageable pageable);

    // ADD MORE QUERIES HERE
}
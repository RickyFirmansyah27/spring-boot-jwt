package com.myapp.service;

import java.util.List;
import java.util.Optional;
import java.util.Collections;


import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myapp.model.Entity.Product;
import com.myapp.model.Repository.ProductRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepo productRepository;

    public Product createOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.map(Collections::singletonList).orElseGet(Collections::emptyList);
    }

    public void deleteProduct(Product product, Long id) {
        productRepository.deleteById(id);
    }

    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    public void deleteProductById(Product product, Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> filteredProduct(Long id, String name, String description, Integer price, Pageable pageable) {
        return productRepository.filteredProduct(id, name, description, price, pageable);
    }
}

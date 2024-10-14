package com.myapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myapp.model.Entity.Product;
import com.myapp.response.BaseResponse;
import com.myapp.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @PostMapping
    public BaseResponse<Product> createProduct(@Valid @RequestBody Product product) {
        logger.info("[ProductController.createProduct]");
        try {
            product = productService.createOrUpdate(product);

            logger.info("[ProductController.createProduct - success {}]", product);
            return new BaseResponse<>("success", "Product created successfully", product);
        } catch (Exception e) {
            logger.error("Error creating product", e);
            return new BaseResponse<>("error", "Failed to create product", null);
        }
    }
    

    @GetMapping()
    public BaseResponse<List<Product>> getListProduct(
        @RequestParam(required = false) Long id, 
        @RequestParam(required = false) String name, 
        @RequestParam(required = false) String description, 
        @RequestParam(required = false) Integer price,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        logger.info("[ProductController.getListProduct]");

        Pageable pageable = PageRequest.of(page > 0 ? page - 1 : 0, size);
        List<Product> products = productService.filteredProduct(id, name, description, price, pageable);

        if (products.isEmpty()) {
            logger.info("[ProductController.getListProduct - error]");
            return new BaseResponse<>("error", "Product not found", products);
        }

        logger.info("[ProductController.getListProduct - {}]", products);
        return new BaseResponse<>("success", "Product fetched successfully", products);
    }

    @GetMapping("/{id}")
    public BaseResponse<List<Product>> getProductById(@PathVariable("id") Long id) {
        logger.info("[ProductController.getProductById]");

        List<Product> products = productService.findProductById(id);

        if (products.isEmpty()) {
            logger.info("[ProductController.getProductById - error]");
            return new BaseResponse<>("error", "Product not found", products);
        }

        logger.info("[ProductController.getProductById - {}]", products);
        return new BaseResponse<>("success", "Product fetched successfully", products);
    }

    @PutMapping()
    public BaseResponse<Product> updateProduct(@RequestBody Product product) {
        logger.info("[ProductController.updateProduct]");
        try {
            product = productService.createOrUpdate(product);

            logger.info("[ProductController.getListProduct - success {}]", product);
            return new BaseResponse<>("success", "Product updated successfully", product);
        } catch (Exception e) {
            logger.error("Error creating product", e);
            return new BaseResponse<>("error", "Failed to updat product", null);
        }
    }
}

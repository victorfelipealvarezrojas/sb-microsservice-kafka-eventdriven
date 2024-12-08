package com.valvarez.ws.products.controller;

import com.valvarez.ws.products.dto.CreateProductRestModel;
import com.valvarez.ws.products.error.ErrorMessage;
import com.valvarez.ws.products.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Date;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/products")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateProductRestModel product) {
        String productId = null;
        try {
            productId = productService.createProduct(product);
        } catch (Exception e) {
           e.printStackTrace();
           return ResponseEntity
                   .status(INTERNAL_SERVER_ERROR)
                   .body(new ErrorMessage(new Date(), e.getMessage(), "Error creating product").toString());
        }
        return ResponseEntity.status(CREATED).body("Product created:" + productId);
    }
}
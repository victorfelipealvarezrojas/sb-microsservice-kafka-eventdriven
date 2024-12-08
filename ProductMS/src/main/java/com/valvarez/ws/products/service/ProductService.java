package com.valvarez.ws.products.service;

import com.valvarez.ws.products.dto.CreateProductRestModel;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateProductRestModel product) throws Exception;
}
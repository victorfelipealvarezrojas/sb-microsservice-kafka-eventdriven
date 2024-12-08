package com.valvarez.ws.core;

import java.math.BigDecimal;

public class ProductCreatedEvent {
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer cuantity;

    public ProductCreatedEvent() {}

    public ProductCreatedEvent(String productId, String title, BigDecimal price, Integer cuantity) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.cuantity = cuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getCuantity() {
        return cuantity;
    }

    public void setCuantity(Integer cuantity) {
        this.cuantity = cuantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

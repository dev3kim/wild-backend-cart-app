package com.example.demo.models;

public class Product {
    private EntityId id;
    private String name;
    private Money unitPrice;

    public Product(Money unitPrice, String name) {
        this.id = new EntityId();
        this.unitPrice = unitPrice;
        this.name = name;
    }

    public EntityId getProductId() {
        return id;
    }

    public Money getUnitPrice() {
        return unitPrice;
    }
}


package com.example.demo.infrastructure;

import com.example.demo.models.Product;
import com.mongodb.client.MongoDatabase;

public class ProductDAO {
    private final MongoDatabase mongoDatabase;

    public ProductDAO(MongoDatabase mongoDatabase) {
        this.mongoDatabase = mongoDatabase;
    }

    public Product find() {

    }
}

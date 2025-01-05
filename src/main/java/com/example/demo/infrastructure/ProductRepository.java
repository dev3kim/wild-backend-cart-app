package com.example.demo.infrastructure;

import com.example.demo.models.Product;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private final MongoCollection<Document> collection;

    public ProductRepository(MongoDatabase mongoDatabase) {
        this.collection = mongoDatabase.getCollection("products");
    }

    public Product find(String productId) {
        Document document = collection.find(
                Filters.eq("_id", new ObjectId(productId))
        ).first();

        if (document == null) {
            return null;
        }

        return mapToModel(document);
    }

    public List<Product> findAllByIds(List<String> productIds) {
        List<Document> documents = new ArrayList<>();
        collection.find(
                Filters.in("_id", productIds.stream().map(ObjectId::new).toList())
        ).into(documents);

        return documents.stream().map(this::mapToModel).toList();
    }

    private Product mapToModel(Document document) {
        return new Product(
                document.getObjectId("id").toString(),
                document.getString("name"),
                document.getInteger("price"));
    }
}

package com.example.demo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class MongoTest {

    @Test
    void readProducts() {
        //환경변수
        String mongoURL = "mongodb://localhost:27017";
        String mongoDatabase = "demo";
        //db 접속
        MongoClient client = MongoClients.create(mongoURL);
        //db 선택 -> demo
        MongoDatabase database = client.getDatabase(mongoDatabase);
        //collection 에서 목록 얻기
        MongoCollection<Document> collection = database.getCollection("products");

        List<Document> documents = new ArrayList<>();
        collection.find().into(documents);

        documents.forEach(document -> {
            System.out.println("document.getString(\"name\") = " + document.getString("name"));
            System.out.println("document.getString(\"name\") = " + document.getInteger("price"));
        });
    }
}

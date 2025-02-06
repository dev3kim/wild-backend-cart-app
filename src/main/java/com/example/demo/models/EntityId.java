package com.example.demo.models;

import java.util.UUID;

public record EntityId(
        String id
) {
    public EntityId() {
        this(UUID.randomUUID().toString());
    }
}

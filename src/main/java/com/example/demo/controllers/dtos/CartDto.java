package com.example.demo.controllers.dtos;

import java.util.List;

public record CartDto(
        List<LineItemDto> lineItems,
        int totalPrice
) {

    public static record LineItemDto(
            String id,
            String productId,
            String productName,
            int unitPrice,
            int quantity,
            int totalPrice
    ) {
    }
}

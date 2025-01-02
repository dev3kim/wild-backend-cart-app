package com.example.demo.controllers;

import com.example.demo.controllers.dtos.CartDto;
import com.example.demo.infrastructure.LineItemDAO;
import com.example.demo.infrastructure.ProductDAO;
import com.example.demo.models.LineItem;
import com.example.demo.models.Product;
import com.mongodb.client.MongoDatabase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final MongoDatabase mongoDatabase;

    private final LineItemDAO lineItemDAO;
    private final ProductDAO productDAO;

    public CartController(MongoDatabase mongoDatabase, LineItemDAO lineItemDAO, ProductDAO productDAO) {
        this.mongoDatabase = mongoDatabase;
        this.lineItemDAO = lineItemDAO;
        this.productDAO = productDAO;
    }

    @GetMapping
    CartDto detail() {
        List<LineItem> lineItems = lineItemDAO.findAll();

        lineItems.forEach(lineItem -> {
            String productId = lineItem.getProductId();
            Product product = productDAO.find();

            int unitPrice = product.getPrice();
            int quantity = lineItem.getQuantity();

            lineItem.setProductName(product.getName());
            lineItem.setUnitPrice(product.getPrice());
            lineItem.setTotalPrice(unitPrice * quantity);
        });

        int totalPrice = lineItems.stream()
                .mapToInt(LineItem::getTotalPrice)
                .sum();

        return new CartDto(lineItems.stream()
                .map(this::mapToDto).toList(), totalPrice);
    }

    private CartDto.LineItemDto mapToDto(LineItem lineItem) {
        //TODO: 에러처리 필요함.
        return new CartDto.LineItemDto(
                lineItem.getId(),
                lineItem.getProductId(),
                lineItem.getProductName(),
                lineItem.getUnitPrice(),
                lineItem.getQuantity(),
                lineItem.getTotalPrice()
        );
    }
}

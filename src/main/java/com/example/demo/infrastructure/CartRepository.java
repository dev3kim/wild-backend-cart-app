package com.example.demo.infrastructure;

import com.example.demo.models.Cart;
import com.example.demo.models.LineItem;
import com.example.demo.models.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CartRepository {
    private final LineItemRepository lineItemRepository;
    private final ProductRepository productRepository;

    public CartRepository(LineItemRepository lineItemRepository, ProductRepository productRepository) {
        this.lineItemRepository = lineItemRepository;
        this.productRepository = productRepository;
    }

    public Cart find() {
        List<LineItem> lineItems = lineItemRepository.findAll();

        lineItems.forEach(lineItem -> {
            String productId = lineItem.getProductId();
            Product product = productRepository.find(productId);
            lineItem.setProduct(product);
        });

        return new Cart(lineItems);
    }

    public void save(Cart cart) {
        cart.getLineItems().forEach(lineItem -> {
            if (lineItem.getId() == null) {
                lineItemRepository.add(lineItem);
                return;
            }
            lineItemRepository.update(lineItem);
        });
    }
}

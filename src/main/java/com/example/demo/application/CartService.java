package com.example.demo.application;

import com.example.demo.infrastructure.CartRepository;
import com.example.demo.infrastructure.ProductRepository;
import com.example.demo.models.Cart;
import com.example.demo.models.LineItem;
import com.example.demo.models.Product;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getCart() {
        Cart cart = cartRepository.find();

        List<String> productIds = cart.getLineItems().stream().map(LineItem::getProductId).toList();

        Map<String, Product> products = new HashMap<>();
        productRepository.findAllByIds(productIds).forEach(product -> {
            products.put(product.getId(), product);
        });

        cart.getLineItems().forEach(lineItem -> {
            Product product = products.get(lineItem.getProductId());
            lineItem.setProduct(product);
        });

        return cart;
    }

    public void addProduct(String productId, int quantity) {
        //1. 도메인 모델 준비
        Cart cart = getCart();
        Product product = productRepository.find(productId);

        //2. 도메인 모델이 도메인 로직 실행
        cart.addProduct(product, quantity);

        //3. 데이터베이스에 반영
        cartRepository.save(cart);
    }
}

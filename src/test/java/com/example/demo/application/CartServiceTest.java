package com.example.demo.application;

import com.example.demo.infrastructure.CartRepository;
import com.example.demo.infrastructure.ProductRepository;
import com.example.demo.models.Cart;
import com.example.demo.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CartServiceTest {
    private Product product;

    private Cart cart;

    private ProductRepository productRepository;
    private CartRepository cartRepository;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        product = new Product("product-1", "product #1", 5_000);

        Cart cart = new Cart(List.of());

        cartRepository = mock(CartRepository.class);

        given(cartRepository.find()).willReturn(cart);

        productRepository = mock(ProductRepository.class);

        given(productRepository.find(product.getId())).willReturn(product);

        cartService = new CartService(cartRepository, productRepository);
    }

    @Test
    void getCart() {

        //given
        //

        //when
        Cart cart = cartService.getCart();

        //then
        assertThat(cart.getTotalPrice()).isEqualTo(0);
    }


    @Test
    @DisplayName("비어있는 장바구니에 상품 담기")
    void addProduct() {
        //given
        String productId = product.getId();
        int quantity = 2;

        //when
        cartService.addProduct(productId, quantity);

        //then
        verify(cartRepository).save(any());
    }
}

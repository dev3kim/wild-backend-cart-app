package com.example.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CartTest {
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        product1 = new Product("product-1", "product #1", 5_000);
        product2 = new Product("product-2", "product #2", 3_000);
    }

    @Test
    void getLineItem() {
        //given
        int quantity1 = 2;
        int quantity2 = 3;
        LineItem lineItem1 = createLineItem(product1, quantity1);
        LineItem lineItem2 = createLineItem(product2, quantity2);


        //when
        Cart cart = new Cart(List.of(lineItem1, lineItem2));

        //then
        assertThat(cart.getLineItem(product1.getId())).isEqualTo(lineItem1);
        assertThat(cart.getLineItem(product2.getId())).isEqualTo(lineItem2);
        assertThat(cart.getLineItem("test123")).isNull();
    }

    @Test
    @DisplayName("비어있는 장바구니에 상품 담기")
    void addProduct() {
        //given
        int quantity = 2;

        Cart cart = new Cart(List.of());
        //when
        cart.addProduct(product1, quantity);

        //then
        List<LineItem> lineItems = cart.getLineItems();
        assertThat(lineItems).hasSize(1);
        assertThat(lineItems.getFirst().getQuantity()).isEqualTo(quantity);
    }

    @Test
    @DisplayName("장바구니에 없는 상품 담기")
    void addNewProduct() {
        //given
        int quantity = 2;

        Cart cart = new Cart(List.of(createLineItem(product2, 3)));

        //when
        cart.addProduct(product1, quantity);

        //then
        List<LineItem> lineItems = cart.getLineItems();
        assertThat(lineItems).hasSize(2);
        assertThat(lineItems.get(1).getQuantity()).isEqualTo(quantity);
        assertThat(lineItems.get(1).getUnitPrice()).isEqualTo(product1.getPrice());
    }

    @Test
    @DisplayName("장바구니에 이미 있는 상품 담기")
    void addExistingProduct() {
        //given
        int quantity = 2;
        int oldQuantity = 3;

        Cart cart = new Cart(List.of(createLineItem(product1, oldQuantity)));

        //when
        cart.addProduct(product1, quantity);

        //then
        List<LineItem> lineItems = cart.getLineItems();
        assertThat(lineItems).hasSize(1);
        assertThat(lineItems.getFirst().getQuantity()).isEqualTo(quantity + oldQuantity);
    }

    @Test
    @DisplayName("getTotalPrice - 빈 장바구니")
    void getTotalPrice() {
        Cart cart = new Cart(List.of());

        assertThat(cart.getTotalPrice()).isEqualTo(0);
    }

    @Test
    @DisplayName("getTotalPrice - 상품이 하나 담겼을 때")
    void getTotalPriceWithLineItem() {
        Product product = new Product("product-1", "Product #1", 5_000);
        int quantity = 2;

        LineItem lineItem = createLineItem(product, quantity);

        Cart cart = new Cart(List.of(lineItem));

        assertThat(cart.getTotalPrice()).isEqualTo(product.getPrice() * quantity);
    }

    @Test
    @DisplayName("장바구니에 있는 여러 상품의 가격을 모두 더해서 총 가격을 구한다.")
    void calculateTotalPriceWithManyLineItems() {
        //given
        int quantity1 = 2;
        int quantity2 = 3;

        //when
        Cart cart = new Cart(List.of(
                createLineItem(product1, quantity1),
                createLineItem(product2, quantity2)));

        //then
        assertThat(cart.getTotalPrice())
                .isEqualTo(product1.getPrice() * quantity1 +
                        product2.getPrice() * quantity2);
    }

    private static LineItem createLineItem(Product product, int quantity) {
        LineItem lineItem = new LineItem(product.getId(), quantity);
        lineItem.setProduct(product);
        return lineItem;
    }
}

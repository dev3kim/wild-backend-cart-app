package com.example.demo.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LineItemTest {

    @Test
    void addQuantity() {
        //given
        String productId = "product-1";
        int quantity = 2;
        int delta = 3;

        LineItem lineItem = new LineItem(productId, quantity);

        //when
        lineItem.addQuantity(delta);

        assertThat(lineItem.getQuantity()).isEqualTo(quantity + delta);
    }

    @Test
    void setProductAndGetTotalPrice() {
        //given
        Product product = new Product("product-1", "Product #1", 5_000);
        int quantity = 2;

        LineItem lineItem = new LineItem(product.getId(), quantity);

        //when
        lineItem.setProduct(product);

        //then
        assertThat(lineItem.getProductName()).isEqualTo(product.getName());
        assertThat(lineItem.getUnitPrice()).isEqualTo(product.getPrice());
        assertThat(lineItem.getTotalPrice()).isEqualTo(product.getPrice() * quantity);
    }
}

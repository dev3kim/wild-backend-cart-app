package com.example.demo.models;

import com.example.demo.exception.TooManyItemsException;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static final int MAX_ITEM_QUANTITY = 20;

    private EntityId id;

    private List<LineItem> lineItems = new ArrayList<>();   // 품목정보

    private Money getTotalAmount() throws Exception {
        if (lineItems == null) {
            return new Money(Currency.KRW, 0);
        }

        return lineItems.stream()
                .map(LineItem::getAmount)
                .reduce(new Money(Currency.KRW, 0), Money::addMoney);
    }

    public void addProduct(Product product, int quantity) throws Exception {
        // tooManyItems 체크
        if (this.checkTooManyItems(quantity)) {
            throw new TooManyItemsException();
        }

        if (this.existsProduct(product)) {
            LineItem lineItem = this.lineItems.stream()
                    .filter(item -> item.getProductId().equals(product.getProductId()))
                    .findFirst().orElseThrow();

            lineItem.addQuantity(quantity);

            return;
        }

        if (this.lineItems == null || this.lineItems.isEmpty()) {
            this.lineItems = new ArrayList<>();
        }

        this.lineItems.add(new LineItem(product, quantity));
    }

    private boolean checkTooManyItems(int quantity) {
        if (this.lineItems == null || this.lineItems.isEmpty()) return false;

        int totalQuantity = this.lineItems.stream().mapToInt(LineItem::getQuantity).sum();

        return totalQuantity + quantity > MAX_ITEM_QUANTITY;
    }

    private boolean existsProduct(Product product) {
        if (this.lineItems == null || this.lineItems.isEmpty()) return false;

        return this.lineItems.stream().anyMatch(item -> item.getProductId().equals(product.getProductId()));
    }

    public List<LineItem> getCartItems() {
        return this.lineItems;
    }

    public void clearCart() {
        this.lineItems = new ArrayList<>();
    }
}

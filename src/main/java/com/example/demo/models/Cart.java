package com.example.demo.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private List<LineItem> lineItems;

    public Cart(List<LineItem> lineItems) {
        this.lineItems = new ArrayList(lineItems);
    }

    public List<LineItem> getLineItems() {
        return Collections.unmodifiableList(lineItems);
    }

    public int getTotalPrice() {
        return lineItems.stream()
                .mapToInt(LineItem::getTotalPrice)
                .sum();
    }

    public LineItem getLineItem(String productId) {
        return lineItems.stream()
                .filter(l -> l.getProductId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public void addProduct(Product product, int quantity) {
        LineItem lineItem = getLineItem(product.getId());

        if (lineItem != null) {
            lineItem.addQuantity(quantity);
            return;
        }

        lineItem = new LineItem(product.getId(), quantity);
        lineItem.setProduct(product);
        lineItems.add(lineItem);
    }
}

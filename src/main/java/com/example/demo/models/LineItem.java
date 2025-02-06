package com.example.demo.models;

public class LineItem {
    private final EntityId id;

    private Product product;  // 품목
    private int quantity;

    public LineItem(Product product, int quantity) {
        this.id = new EntityId();
        this.product = product;
        this.quantity = quantity;
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public Money getAmount() {
        if (this.product == null) {
            return new Money(Currency.KRW, 0);
        }

        return this.product.getUnitPrice().multiply(this.quantity);
    }

    public int getQuantity() {
        return this.quantity;
    }

    public EntityId getProductId() {
        if (this.product == null) {
            return null;
        }

        return this.product.getProductId();
    }
}

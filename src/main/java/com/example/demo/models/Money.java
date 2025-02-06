package com.example.demo.models;

public record Money(
        Currency currency,
        int amount
) {
    public Money multiply(int quantity) {
        return new Money(this.currency, amount * quantity);
    }

    public Money addMoney(Money money) {
        if (this.currency != money.currency) {
            throw new IllegalArgumentException("currency 다름!");
        }

        return new Money(currency, this.amount + money.amount);
    }
}

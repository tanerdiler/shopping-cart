package com.trendyol.shoppingcart;

public class UnsupportedDiscountTypeException extends Throwable {

    private final Discount discount;

    public UnsupportedDiscountTypeException(String message, Discount discount) {
        super(message);
        this.discount = discount;
    }
}

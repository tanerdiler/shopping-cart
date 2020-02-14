package com.trendyol.shoppingcart;

public class Coupon {

    private Price limit;

    private Discount discount;

    public Coupon(Price limit, Discount discount) throws UnsupportedDiscountTypeException {

        if (discount.isAmount()) {
            throw new UnsupportedDiscountTypeException("Coupon supports only discount with rate.", discount);
        }

        this.limit = limit;
        this.discount = discount;
    }

    public Price getLimit() {
        return limit;
    }

    public Discount getDiscount() {
        return discount;
    }
}

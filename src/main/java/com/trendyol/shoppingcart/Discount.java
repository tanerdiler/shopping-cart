package com.trendyol.shoppingcart;

import java.util.Objects;

import static com.trendyol.shoppingcart.DiscountType.AMOUNT;
import static com.trendyol.shoppingcart.DiscountType.RATE;

public class Discount implements Comparable<Discount> {

    private double value;

    private DiscountType type;

    public Discount(double value, DiscountType type) {

        this.value = value;
        this.type = type;
    }

    public static Discount rate(double value) {
        return new Discount(value, RATE);
    }

    public static Discount amount(double value) {
        return new Discount(value, AMOUNT);
    }

    public double getValue() {
        return value;
    }

    public boolean isRate() {
        return RATE == type;
    }

    public boolean isAmount() {
        return AMOUNT == type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Discount discount = (Discount) o;
        return Double.compare(discount.value, value) == 0 && type == discount.type;
    }

    @Override
    public int hashCode() {

        return Objects.hash(value, type);
    }

    public Price applyTo(Price price) {
        if (RATE == type) {
            return price.multiply((100-value)/100);
        } else {
            return price.minus(value);
        }
    }

    @Override
    public int compareTo(Discount target) {
        return Double.compare(this.value, target.value);
    }
}

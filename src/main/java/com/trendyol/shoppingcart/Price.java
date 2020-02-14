package com.trendyol.shoppingcart;


import java.math.BigDecimal;
import java.util.Objects;

public class Price implements Value<BigDecimal> {

    public static final Price ZERO = Price.of(0.0);

    private final BigDecimal value;

    private Price(BigDecimal value) {
        this.value = value;
    }

    public static Price of(BigDecimal value) {
        return new Price(value);
    }

    public static Price of(double value) {
        return new Price(new BigDecimal(value));
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Price price = (Price) o;
        return Objects.equals(value, price.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }

    public Price multiply(int multiplier) {
        return Price.of(value.multiply(new BigDecimal(multiplier)));
    }

    public Price multiply(double multiplier) {
        return Price.of(value.multiply(new BigDecimal(multiplier)));
    }

    public Price sum(Price price) {
        return Price.of(value.add(price.getValue()));
    }

    public Price minus(double amount) {
        return Price.of(value.subtract(new BigDecimal(amount)));
    }

    public Price minus(Price priceAfterDiscount) {
        return Price.of(value.subtract(priceAfterDiscount.getValue()));
    }

    @Override
    public String toString() {
        return "Price{" + "value=" + value + '}';
    }
}

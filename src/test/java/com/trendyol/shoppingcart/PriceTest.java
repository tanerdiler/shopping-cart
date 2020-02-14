package com.trendyol.shoppingcart;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class PriceTest {

    @Test
    public void should_hold_amount_of_money(){
        // GIVEN
        Price price = Price.of(100.0);

        // THEN
        assertEquals(new BigDecimal(100.0), price.getValue());
    }

    @Test
    public void should_be_equal_to_other_price_with_same_value(){
        // GIVEN
        Price price = Price.of(100.0);
        Price priceToTest = Price.of(100.0);

        // THEN
        assertEquals(price, priceToTest);
    }
}

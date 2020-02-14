package com.trendyol.shoppingcart;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiscountTest {

    @Test
    public void should_be_a_rate() {
        // GIVEN
        Discount discount = new Discount(20.0, DiscountType.RATE);
        // WHEN
        // THEN
        assertTrue(discount.isRate());
        assertFalse(discount.isAmount());
    }

    @Test
    public void should_have_a_value_for_rate_type() {
        // GIVEN
        Discount discount = new Discount(20.0, DiscountType.RATE);
        // WHEN
        // THEN
        assertEquals(20.0, discount.getValue());
    }

    @Test
    public void should_be_an_amount() {
        // GIVEN
        Discount discount = new Discount(200.0, DiscountType.AMOUNT);
        // WHEN
        // THEN
        assertTrue(discount.isAmount());
        assertFalse(discount.isRate());
    }

    @Test
    public void should_have_a_value_for_amount_type() {
        // GIVEN
        Discount discount = new Discount(200.0, DiscountType.AMOUNT);
        // WHEN
        // THEN
        assertEquals(200.0, discount.getValue());
    }

}

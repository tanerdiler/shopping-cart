package com.trendyol.shoppingcart;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

public class CouponTest {

    @Test
    public void must_have_a_limit_to_check_cart_total() throws UnsupportedDiscountTypeException {
        // GIVEN
        Coupon coupon = new Coupon(Price.of(100.0), Discount.rate(300.0));
        // WHEN
        // THEN
        assertEquals(100.0, coupon.getLimit().getValue().doubleValue(),0.001);
    }

    @Test
    public void should_throw_UnsupportedDiscountTypeException_have_a_limit_to_check_cart_total() {
        UnsupportedDiscountTypeException exception = assertThrows(UnsupportedDiscountTypeException.class, ()-> new Coupon(Price.of(100.0), new Discount(300.0, DiscountType.AMOUNT)));
        assertEquals("Coupon supports only discount with rate.", exception.getMessage());
    }


}

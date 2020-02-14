package com.trendyol.shoppingcart;

import org.junit.jupiter.api.Test;

import static com.trendyol.shoppingcart.DiscountType.RATE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CampaignTest {

    @Test
    public void must_have_a_discount() {
        // GIVEN
        Category category = new Category("Phone");
        Campaign campaign = new Campaign(category, Discount.rate(20.0), 5);
        // WHEN
        // THEN
        assertEquals(new Discount(20.0, RATE), campaign.getDiscount());
    }

    @Test
    public void must_target_a_category() {
        // GIVEN
        Category category = new Category("Phone");
        Campaign campaign = new Campaign(category, Discount.rate(20.0), 5);
        // WHEN
        // THEN
        assertEquals(new Category("Phone"), campaign.getCategory());
    }

    @Test
    public void must_have_a_min_item_count_to_be_applied() {
        // GIVEN
        Category category = new Category("Phone");
        Campaign campaign = new Campaign(category, Discount.rate(20.0), 5);
        // WHEN
        // THEN
        assertEquals(5, campaign.getMinItemCount());
    }
}

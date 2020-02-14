package com.trendyol.shoppingcart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    @Test
    public void must_have_a_title() {
        // GIVEN
        Category category = new Category("Mobile Phone");
        Product product = new Product("Apple", Price.of(1000.0), category);

        // THEN
        assertEquals("Apple", product.getName());
    }

    @Test
    public void must_have_a_price() {
        // GIVEN
        Category category = new Category("Mobile Phone");
        Product product = new Product("Apple", Price.of(1000.0), category);

        // THEN
        assertEquals(Price.of(1000.0), product.getPrice());
    }

    @Test
    public void must_belong_to_a_category() {
        // GIVEN
        Category category = new Category("Mobile Phone");
        Product product = new Product("Apple", Price.of(1000.0), category);

        // THEN
        Category categoryToTest = new Category("Mobile Phone");
        assertTrue(product.isBelongTo(categoryToTest));
    }

}

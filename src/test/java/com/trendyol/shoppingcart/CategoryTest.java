package com.trendyol.shoppingcart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryTest {

    @Test
    public void must_have_a_title(){
        // GIVEN
        Category category = new Category("Mobile Phone");

        // THEN
        assertEquals("Mobile Phone", category.getTitle());
    }

    @Test
    public void could_be_in_a_parent_category(){
        // GIVEN
        Category parent = new Category("Phone");
        Category parentToTest = new Category("Phone");
        Category category = new Category("Mobile Phone", parent);

        // THEN
        assertTrue(category.isChildOf(parentToTest));
    }

    @Test
    public void should_be_equal_two_instances_with_same_title(){
        // GIVEN
        Category category = new Category("Mobile Phone");
        Category categoryToTest = new Category("Mobile Phone");

        // THEN
        assertEquals(category, categoryToTest);
    }

}

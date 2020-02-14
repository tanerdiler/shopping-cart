package com.trendyol.shoppingcart;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeliveryCostCalculatorTest {

    @Test
    public void should_calc_delivery_cost() {
        // GIVEN
        double costPerDelivery = 1.5;
        double costPerProduct = 3;
        double fixedCost = 2.99;

        Category phoneCategory = new Category("Phone");
        ShoppingCart cart = new ShoppingCart();

        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);

        // WHEN
        cart.addItem(product_1, 3);
        DeliveryCostCalculator calc = new DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);
        DeliveryCost cost = calc.calculateFor(cart);

        // THEN
        assertEquals(7.49, cost.getValue(), 0.01);

    }

    @Test
    public void should_calc_delivery_cost_v2() {
        // GIVEN
        double costPerDelivery = 1.5;
        double costPerProduct = 3;
        double fixedCost = 2.99;

        Category phoneCategory = new Category("Phone");
        Category carCategory = new Category("Car");
        ShoppingCart cart = new ShoppingCart();

        Product product_1 = new Product("Apple", Price.of(100.0), phoneCategory);
        Product product_2 = new Product("LG", Price.of(100.0), phoneCategory);
        Product product_3 = new Product("Ferrari", Price.of(200.0), carCategory);

        // WHEN
        cart.addItem(product_1, 3);
        cart.addItem(product_2, 3);
        cart.addItem(product_3, 3);
        DeliveryCostCalculator calc = new DeliveryCostCalculator(costPerDelivery, costPerProduct, fixedCost);
        DeliveryCost cost = calc.calculateFor(cart);

        // THEN
        assertEquals(14.99, cost.getValue(), 0.01);

    }
}

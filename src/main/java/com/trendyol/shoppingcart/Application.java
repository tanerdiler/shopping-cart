package com.trendyol.shoppingcart;

import static com.trendyol.shoppingcart.CampaignSelectionRule.getRules;

public class Application {

    public static void main(String[] args) throws UnsupportedDiscountTypeException {
        Category phoneCategory = new Category("Phone");
        Category carCategory = new Category("Car");
        ShoppingCart cart = new ShoppingCart(getRules());

        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);
        Product product_2 = new Product("LG", Price.of(110.0), phoneCategory);
        Campaign phoneCampaign = new Campaign(phoneCategory, Discount.rate(10.0), 3);

        Product product_3 = new Product("Ferrari", Price.of(1000.0), carCategory);
        Product product_4 = new Product("Tofaş", Price.of(50.0), carCategory);
        Campaign carCampaign = new Campaign(carCategory, Discount.rate(20.0), 5);

        Coupon coupon = new Coupon(Price.of(100.0), Discount.rate(10));

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 3);
        cart.addItem(product_3, 2);
        cart.addItem(product_4, 4);

        cart.applyDiscounts(phoneCampaign, carCampaign);
        cart.applyCoupons(coupon);

        cart.print();
    }
}

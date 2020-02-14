package com.trendyol.shoppingcart;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShoppingCartTest {

    @Test
    public void should_hold_products_with_quantity(){
        // GIVEN
        Category phoneCategory = new Category("Phone");
        Category carCategory = new Category("Car");
        ShoppingCart cart = new ShoppingCart();
        Product product_1 = new Product("Apple", Price.of(100.0), phoneCategory);
        Product product_2 = new Product("Wheel", Price.of(500.0), carCategory);

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 4);

        // THEN
        assertEquals( 1, cart.getQuantityOf(product_1));
        assertEquals(4, cart.getQuantityOf(product_2));
    }

    @Test
    public void should_calc_total_quantity(){
        // GIVEN
        Category phoneCategory = new Category("Phone");
        Category carCategory = new Category("Car");
        ShoppingCart cart = new ShoppingCart();
        Product product_1 = new Product("Apple", Price.of(100.0), phoneCategory);
        Product product_2 = new Product("Wheel", Price.of(500.0), carCategory);

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 4);

        // THEN
        assertEquals( 5, cart.getTotalQuantity());
    }

    @Test
    public void should_calc_total_amount(){
        // GIVEN
        Category phoneCategory = new Category("Phone");
        Category carCategory = new Category("Car");
        ShoppingCart cart = new ShoppingCart();
        Product product_1 = new Product("Apple", Price.of(100.0), phoneCategory);
        Product product_2 = new Product("Wheel", Price.of(500.0), carCategory);

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 4);

        // THEN
        assertEquals(Price.of(2100.0), cart.getTotalAmount());
    }

    @Test
    public void should_apply_discount_if_has_more_than_campaign_limit() throws CampaignNotAppliedException {
        // GIVEN
        Category phoneCategory = new Category("Phone");
        ShoppingCart cart = new ShoppingCart();
        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);
        Product product_2 = new Product("LG", Price.of(110.0), phoneCategory);
        Campaign campaign = new Campaign(phoneCategory, Discount.rate(20.0), 3);

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 4);
        cart.applyDiscounts(campaign);

        // THEN
        assertEquals(520.0, cart.getTotalAmountAfterDiscounts().getValue().doubleValue(), 0.001);
    }


    @Test
    public void should_apply_discount_if_has_items_that_count_is_equal_to_campaign_limit() throws CampaignNotAppliedException {
        // GIVEN
        Category phoneCategory = new Category("Phone");
        ShoppingCart cart = new ShoppingCart();
        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);
        Product product_2 = new Product("LG", Price.of(110.0), phoneCategory);
        Campaign campaign = new Campaign(phoneCategory, Discount.rate(20.0), 6);

        // WHEN
        cart.addItem(product_1, 2);
        cart.addItem(product_2, 4);
        cart.applyDiscounts(campaign);

        // THEN
        assertEquals(688.0, cart.getTotalAmountAfterDiscounts().getValue().doubleValue(), 0.001);
    }


    @Test
    public void should_not_apply_discount_if_item_count_is_less_than_campaign_limit() throws CampaignNotAppliedException {
        // GIVEN
        Category phoneCategory = new Category("Phone");
        ShoppingCart cart = new ShoppingCart();
        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);
        Product product_2 = new Product("LG", Price.of(110.0), phoneCategory);
        Campaign campaign = new Campaign(phoneCategory, Discount.rate(20.0), 10);

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 4);
        cart.applyDiscounts(campaign);

        // THEN
        assertEquals(650.0, cart.getTotalAmountAfterDiscounts().getValue().doubleValue(), 0.001);
    }

    @Test
    public void should_apply_discount_to_target_category() throws CampaignNotAppliedException {
        // GIVEN
        Category phoneCategory = new Category("Phone");
        Category carCategory = new Category("Car");
        ShoppingCart cart = new ShoppingCart();
        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);
        Product product_2 = new Product("LG", Price.of(110.0), phoneCategory);
        Campaign campaign = new Campaign(carCategory, Discount.rate(20.0), 10);

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 4);
        cart.applyDiscounts(campaign);

        // THEN
        assertEquals(650.0, cart.getTotalAmountAfterDiscounts().getValue().doubleValue(), 0.001);
    }

    @Test
    public void should_apply_max_discount_to_target_category() throws CampaignNotAppliedException {
        // GIVEN
        Category phoneCategory = new Category("Phone");
        ShoppingCart cart = new ShoppingCart();
        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);
        Product product_2 = new Product("LG", Price.of(110.0), phoneCategory);

        Campaign campaign1 = new Campaign(phoneCategory, Discount.rate(20.0), 10);
        Campaign campaign2 = new Campaign(phoneCategory, Discount.rate(40.0), 10);

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 4);
        cart.applyDiscounts(campaign1, campaign2);

        // THEN
        assertEquals(389.999, cart.getTotalAmountAfterDiscounts().getValue().doubleValue(), 0.001);
    }

    @Test
    public void should_apply_discount_of_campaigns_to_only_target_cateogory() throws CampaignNotAppliedException {
        // GIVEN
        Category phoneCategory = new Category("Phone");
        Category carCategory = new Category("Car");
        ShoppingCart cart = new ShoppingCart();

        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);
        Product product_2 = new Product("LG", Price.of(110.0), phoneCategory);
        Campaign phoneCampaign = new Campaign(phoneCategory, Discount.rate(10.0), 3);

        Product product_3 = new Product("Ferrari", Price.of(1000.0), carCategory);
        Product product_4 = new Product("Tofaş", Price.of(50.0), carCategory);
        Campaign carCampaign = new Campaign(carCategory, Discount.rate(20.0), 5);

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 3);
        cart.addItem(product_3, 2);
        cart.addItem(product_4, 4);
        cart.applyDiscounts(phoneCampaign, carCampaign);

        // THEN
        assertEquals(2246.0, cart.getTotalAmountAfterDiscounts().getValue().doubleValue(), 0.001);
    }

    // should_not_apply_a_campaign_more_than_once

    @Test
    public void should_apply_coupon() throws UnsupportedDiscountTypeException {
        // GIVEN
        Category phoneCategory = new Category("Phone");
        Category carCategory = new Category("Car");
        ShoppingCart cart = new ShoppingCart();

        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);
        Product product_2 = new Product("LG", Price.of(110.0), phoneCategory);
        Coupon coupon = new Coupon(Price.of(100.0), Discount.rate(10));


        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 3);
        cart.applyCoupons(coupon);

        // THEN
        assertEquals(486.0, cart.getTotalAmountAfterDiscounts().getValue().doubleValue(),0.001);
    }

    @Test
    public void should_not_apply_coupon_if_amount_is_less_than_limit() throws UnsupportedDiscountTypeException {
        // GIVEN
        Category phoneCategory = new Category("Phone");
        ShoppingCart cart = new ShoppingCart();

        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);
        Product product_2 = new Product("LG", Price.of(110.0), phoneCategory);

        Coupon coupon = new Coupon(Price.of(1000.0), Discount.rate(10));

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 3);
        cart.applyCoupons(coupon);

        // THEN
        assertEquals(540.0, cart.getTotalAmountAfterDiscounts().getValue().doubleValue(), 0.001);
    }

    @Test
    public void should_apply_campaign_of_parent_category_if_exists() throws UnsupportedDiscountTypeException {
        // GIVEN
        Category phoneCategory = new Category("Phone");
        Category iosCategory = new Category("IOS Phone", phoneCategory);
        Category iphoneCategory = new Category("IPhone", iosCategory);

        ShoppingCart cart = new ShoppingCart();

        Product product_1 = new Product("IPhone 8", Price.of(210.0), iphoneCategory);
        Product product_2 = new Product("MyPhone 5", Price.of(110.0), iosCategory);

        Campaign phoneCampaign = new Campaign(phoneCategory, Discount.rate(10.0), 3);
        Campaign iphoneCampaign = new Campaign(iphoneCategory, Discount.rate(20.0), 3);

        // WHEN
        cart.addItem(product_1, 1);
        cart.addItem(product_2, 3);
        cart.applyDiscounts(phoneCampaign,iphoneCampaign);

        // THEN
        assertEquals(465.0, cart.getTotalAmountAfterDiscounts().getValue().doubleValue(), 0.001);
    }

//    @Test
//    @Ignore
//    public void should_not_apply_campaigns_after_a_coupon_applied() throws UnsupportedDiscountTypeException {
//        // GIVEN
//        Category phoneCategory = new Category("Phone");
//        ShoppingCart cart = new ShoppingCart();
//
//        Product product_1 = new Product("Apple", Price.of(210.0), phoneCategory);
//        Product product_2 = new Product("LG", Price.of(110.0), phoneCategory);
//
//        Campaign phoneCampaign = new Campaign(phoneCategory, Discount.rate(10.0), 3);
//        Coupon coupon = new Coupon(Price.of(100.0), Discount.rate(10));
//
//        // WHEN
//        cart.addItem(product_1, 1);
//        cart.addItem(product_2, 3);
//        cart.applyCoupons(coupon);
//
//        CampaignNotAppliedException exception = assertThrows(CampaignNotAppliedException.class, ()-> cart.applyDiscounts(phoneCampaign));
//
//        // THEN
//        assertEquals("Campaign denied after a coupon", exception.getMessage());
//    }

    @Test
    public void complex_test_1() throws CampaignNotAppliedException, UnsupportedDiscountTypeException {
        // GIVEN
        Category phoneCategory = new Category("Phone");
        Category carCategory = new Category("Car");
        ShoppingCart cart = new ShoppingCart();

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

        // THEN
        assertEquals(2740.0, cart.getTotalAmount().getValue().doubleValue(), 0.001);
        assertEquals(2021.4, cart.getTotalAmountAfterDiscounts().getValue().doubleValue(), 0.001);
        assertEquals(493.999, cart.getCampaignDiscount(), 0.001);
        assertEquals(224.599, cart.getCouponDiscount(), 0.001);
        assertEquals(4.99, cart.getDeliveryCost());
    }

    @Test
    public void should_print() throws CampaignNotAppliedException, UnsupportedDiscountTypeException {
        // GIVEN
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Category phoneCategory = new Category("Phone");
        Category carCategory = new Category("Car");
        ShoppingCart cart = new ShoppingCart();

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

        // THEN
        cart.print();
        // @formatter:off
        assertEquals("\nCategory ---> Car\n"
                             + "Product Name    Quantity        Unit Price      Total Price     Total Discount \n"
                             + "Ferrari         2                     1000.00         1440.00          560.00\n"
                             + "Tofaş           4                       50.00          144.00           56.00\n"
                             + "\n"
                             + "Category ---> Phone\n"
                             + "Product Name    Quantity        Unit Price      Total Price     Total Discount \n"
                             + "Apple           1                      210.00          170.10           39.90\n"
                             + "LG              3                      110.00          267.30           62.70\n"
                             + "\n"
                             + "Total Amount Before Disctoun ---> 2740.00\n" + "Total Amount  ---> 2021.40\n"
                             + "Delivery Cost ---> 4.99\n", outContent.toString());
        // @formatter:on
        System.setOut(originalOut);


    }
}

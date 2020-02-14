package com.trendyol.shoppingcart;

class DeliveryCostCalculator {

    private final double costPerDelivery;

    private final double costPerProduct;

    private final double fixedCost;

    public DeliveryCostCalculator(double costPerDelivery, double costPerProduct, double fixedCost) {

        this.costPerDelivery = costPerDelivery;
        this.costPerProduct = costPerProduct;
        this.fixedCost = fixedCost;
    }

    public DeliveryCost calculateFor(ShoppingCart cart) {

        double numberOfDelivery = cart.getCategoryCount();
        double numberOfProduct = cart.getDistinctProductCount();
        double deliveryCost = numberOfDelivery*costPerDelivery+numberOfProduct*costPerProduct+fixedCost;
        return new DeliveryCost(costPerProduct, costPerDelivery, fixedCost, numberOfDelivery, numberOfProduct, deliveryCost);
    }
}

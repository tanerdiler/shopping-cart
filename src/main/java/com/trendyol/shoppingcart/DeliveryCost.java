package com.trendyol.shoppingcart;

public class DeliveryCost implements Value<Double> {

    private final double costPerProduct;

    private final double costPerDelivery;

    private final double fixedCost;

    private final double numberOfDelivery;

    private final double numberOfProduct;

    private final double deliveryCost;

    public DeliveryCost(double costPerProduct, double costPerDelivery, double fixedCost, double numberOfDelivery, double numberOfProduct,
                        double deliveryCost) {

        this.costPerProduct = costPerProduct;
        this.costPerDelivery = costPerDelivery;
        this.fixedCost = fixedCost;
        this.numberOfDelivery = numberOfDelivery;
        this.numberOfProduct = numberOfProduct;
        this.deliveryCost = deliveryCost;
    }

    @Override
    public Double getValue() {
        return deliveryCost;
    }
}

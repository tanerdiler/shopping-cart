package com.trendyol.shoppingcart;

import com.trendyol.shoppingcart.rules.ChooseCampaignOfNearestCategoryInParentsIfNoExactlyMatchedCampaign;
import com.trendyol.shoppingcart.rules.ChooseCategoryGraphMatchedCampaigns;
import com.trendyol.shoppingcart.rules.ChooseLimitMatchedCampaigns;
import com.trendyol.shoppingcart.rules.ChooseMaxDiscountedCampaigns;

import java.util.*;

import static com.trendyol.shoppingcart.Config.DELIVERY_COST_FIXED;
import static com.trendyol.shoppingcart.Config.DELIVERY_COST_PER_DELIVERY;
import static com.trendyol.shoppingcart.Config.DELIVERY_COST_PER_PRODUCT;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

public class ShoppingCart {

    private Map<Category, List<CartItem>> items = new HashMap();

    private Price totalAmountAfterDiscounts = Price.of(0.0);

    private List<CampaignSelectionRule> campaignSelectionRules = new ArrayList<>();

    public ShoppingCart(List<CampaignSelectionRule> campaignSelectionRules){
        this.campaignSelectionRules = campaignSelectionRules;
    }

    public void addItem(Product product, int quantity) {
        List<CartItem> itemList = items.getOrDefault(product.getCategory(), new ArrayList<>());
        CartItem item = new CartItem(product, quantity);
        itemList.add(item);
        items.put(product.getCategory(), itemList);
        totalAmountAfterDiscounts = totalAmountAfterDiscounts.sum(item.getTotalPrice());
    }

    public int getQuantityOf(Product product) {
        return items.get(product.getCategory())
                    .stream()
                    .filter(item->item.is(product))
                    .mapToInt(item->item.getQuantity())
                    .sum();
    }

    public Price getTotalAmount() {
        return items.values()
                    .stream()
                    .flatMap(l->l.stream())
                    .map(i->i.getTotalPrice())
                    .reduce(Price.of(0.0), Price::sum);
    }

    public double getCouponDiscount() {

        return items.values().stream()
                    .flatMap(l->l.stream())
                    .map(i->i.getCouponDiscount())
                    .reduce(Price.ZERO, (i1,i2)->i1.sum(i2))
                    .getValue()
                    .doubleValue();
    }

    public double getCampaignDiscount() {

        return items.values().stream()
                    .flatMap(l->l.stream())
                    .map(i->i.getCampaignDiscount())
                    .reduce(Price.ZERO, (i1,i2)->i1.sum(i2)).getValue().doubleValue();
    }

    public double getDeliveryCost() {
        return new DeliveryCostCalculator(DELIVERY_COST_PER_DELIVERY,
                                          DELIVERY_COST_PER_PRODUCT,
                                          DELIVERY_COST_FIXED)
                .calculateFor(this)
                .getValue();
    }

    public Price getTotalAmountAfterDiscounts() {
        return items.values()
                    .stream()
                    .flatMap(l->l.stream())
                    .map(i->i.getTotalPriceAfterDiscount())
                    .reduce(Price.ZERO, (p1,p2)->p1.sum(p2));
    }

    public int getTotalQuantity(){
        return items.values()
                    .stream()
                    .flatMap(l->l.stream())
                    .mapToInt(item->item.getQuantity())
                    .sum();
    }

    double getCategoryCount() {

        return items.size();
    }

    double getDistinctProductCount() {
        return items.values()
                    .stream()
                    .flatMap(l->l.stream())
                    .distinct()
                    .count();
    }

    public void applyDiscounts(Campaign... campaigns) {

        Map<Category, List<Campaign>> campaignMap = Arrays.stream(campaigns).collect(groupingBy(c->c.getCategory()));

        for (CampaignSelectionRule rule : campaignSelectionRules) {
            campaignMap = rule.apply(campaignMap, this);
        }

        for (Category category : campaignMap.keySet()){
            Campaign campaignToApply = campaignMap.get(category).get(0);
            items.get(category).stream().forEach(p->p.applyCampaign(campaignToApply));
        }
    }

    private Optional<Category> choosedCategory(Map<Category, Campaign> map, Category productCategory) {

        return productCategory.getCategoriesBottomToTopOrdered().stream().filter(p->map.containsKey(p)).findFirst();
    }

    public void applyCoupons(Coupon coupon) {
        if (isCartAmountGreaterThanCouponLimit(coupon)) {
            applyCouponToEachItem(coupon);
        }
    }

    private void applyCouponToEachItem(Coupon coupon) {
        items.values().stream().flatMap(l->l.stream()).forEach(i->i.applyCoupon(coupon));
    }

    private boolean isCartAmountGreaterThanCouponLimit(Coupon coupon) {
        return getTotalAmountAfterDiscounts().getValue().compareTo(coupon.getLimit().getValue())>=0;
    }

    public void print(){
        items.entrySet().forEach(e-> {
            System.out.println(format("\nCategory ---> %s", e.getKey().getTitle()));
            System.out.println(format("%-15s %-15s %-15s %-15s %-15s", "Product Name", "Quantity", "Unit Price", "Total Price", "Total Discount"));
            e.getValue().stream().forEach(i->i.print());
        });
        System.out.println("");
        System.out.println(format("Total Amount Before Disctoun ---> %.2f", getTotalAmount().getValue()));
        System.out.println(format("Total Amount  ---> %.2f", getTotalAmountAfterDiscounts().getValue()));
        System.out.println(format("Delivery Cost ---> %.2f", getDeliveryCost()));
    }

    public Set<Category> getProductCategories() {
        return items.keySet();
    }

    public int getProductCountBelongTo(Category category) {
        return items.keySet().stream()
                    .filter(c->c.isChildOf(category) || c.equals(category))
                    .flatMap(c->items.get(c).stream())
                    .mapToInt(i->i.getQuantity())
                    .sum();
    }

    private static class CartItem {

        private final Product product;
        private final int quantity;

        private Price campaignDiscount;
        private Price couponDiscount;

        private Coupon appliedCoupon;
        private Campaign appliedCampaign;

        public CartItem(Product product, int quantity) {

            this.product = product;
            this.quantity = quantity;
        }

        public int getQuantity() {
            return quantity;
        }

        public Product getProduct() {
            return product;
        }

        public boolean is(Product product) {
            return this.product.equals(product);
        }

        public Price getTotalPrice() {
            return product.getPrice().multiply(quantity);
        }

        public Price getTotalPriceAfterDiscount() {
            return getTotalPrice().minus(isCouponApplied()?couponDiscount:Price.of(0.0))
                                  .minus(isCampaignApplied()?campaignDiscount:Price.of(0.0));
        }

        private boolean isCouponApplied() {
            return nonNull(appliedCoupon);
        }

        private boolean isCampaignApplied() {
            return nonNull(appliedCampaign);
        }

        public void applyCoupon(Coupon coupon){

            Price totalAmount = getTotalPriceAfterDiscount();
            Price priceAfterDiscount = coupon.getDiscount().applyTo(totalAmount);
            Price discount = totalAmount.minus(priceAfterDiscount);
            couponDiscount = discount;
            appliedCoupon = coupon;
        }

        public void applyCampaign(Campaign campaign) {

            Price totalAmount = getTotalPrice();
            Price priceAfterDiscount = campaign.getDiscount().applyTo(totalAmount);
            Price discount = totalAmount.minus(priceAfterDiscount);
            campaignDiscount = discount;
            appliedCampaign = campaign;
        }

        private Price getTotalDiscount() {
            return campaignDiscount.sum(couponDiscount);
        }

        Price getCampaignDiscount() {
            return campaignDiscount;
        }

        Price getCouponDiscount() {
            return couponDiscount;
        }

        @Override
        public String toString() {
            return "CartItem{" + "product=" + product + ", quantity=" + quantity + ", campaignDiscount=" + campaignDiscount + ", couponDiscount="
                    + couponDiscount + ", appliedCoupon=" + appliedCoupon + ", appliedCampaign=" + appliedCampaign + '}';
        }

        public void print() {
            System.out.println(format("%-15s %-15d %13.2f %15.2f %15.2f", product.getName(), quantity, product.getPrice().getValue(), getTotalPriceAfterDiscount().getValue(), getTotalDiscount().getValue()));
        }
    }
}

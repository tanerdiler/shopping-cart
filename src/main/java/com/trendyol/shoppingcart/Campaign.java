package com.trendyol.shoppingcart;

import java.util.Comparator;

public class Campaign {

    private Category category;

    private Discount discount;

    private int minItemCount;

    public Campaign(Category category, Discount discount, int minItemCount) {

        this.category = category;
        this.discount = discount;
        this.minItemCount = minItemCount;
    }

    public Discount getDiscount() {
        return discount;
    }

    public Category getCategory() {
        return category;
    }

    public int getMinItemCount() {
        return minItemCount;
    }

}

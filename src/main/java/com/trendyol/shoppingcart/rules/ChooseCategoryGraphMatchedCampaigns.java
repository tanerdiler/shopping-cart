package com.trendyol.shoppingcart.rules;

import com.trendyol.shoppingcart.Campaign;
import com.trendyol.shoppingcart.CampaignSelectionRule;
import com.trendyol.shoppingcart.Category;
import com.trendyol.shoppingcart.ShoppingCart;

import java.util.*;

public class ChooseCategoryGraphMatchedCampaigns implements CampaignSelectionRule {

    @Override
    public Map<Category, List<Campaign>> apply(Map<Category, List<Campaign>> campaignList, ShoppingCart shoppingCart) {

        Map<Category, List<Campaign>> productCategoryToCampaigns = new HashMap<>();
        Set<Category> productCategories = shoppingCart.getProductCategories();
        for (Category campaignCategory : campaignList.keySet()) {
            for (Category productCategory : productCategories) {
                List<Category> categoryGraph = productCategory.getCategoriesBottomToTopOrdered();
                if (categoryGraph.contains(campaignCategory)) {
                    List<Campaign> campaignsAppliableToProductCategory = productCategoryToCampaigns.getOrDefault(productCategory, new ArrayList<>());
                    campaignsAppliableToProductCategory.addAll(campaignList.get(campaignCategory));
                    productCategoryToCampaigns.put(productCategory, campaignsAppliableToProductCategory);
                }
            }
        }
        return productCategoryToCampaigns;
    }
}

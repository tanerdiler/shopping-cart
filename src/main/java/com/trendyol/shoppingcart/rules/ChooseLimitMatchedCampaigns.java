package com.trendyol.shoppingcart.rules;

import com.trendyol.shoppingcart.Campaign;
import com.trendyol.shoppingcart.CampaignSelectionRule;
import com.trendyol.shoppingcart.Category;
import com.trendyol.shoppingcart.ShoppingCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseLimitMatchedCampaigns implements CampaignSelectionRule {

    @Override
    public Map<Category, List<Campaign>> apply(Map<Category, List<Campaign>> campaignList, ShoppingCart shoppingCart) {
        Map<Category, List<Campaign>> productCategoryToCampaigns = new HashMap<>();
        for (Map.Entry<Category, List<Campaign>> entry : campaignList.entrySet()) {
            for (Campaign campaign : entry.getValue()) {
               int productCount = shoppingCart.getProductCountBelongTo(campaign.getCategory());
               if (productCount >= campaign.getMinItemCount()) {
                    List<Campaign> campaignsAppliableToProductCategory = productCategoryToCampaigns.getOrDefault(entry.getKey(), new ArrayList<>());
                   campaignsAppliableToProductCategory.add(campaign);
                   productCategoryToCampaigns.put(entry.getKey(), campaignsAppliableToProductCategory);
               }
            }
        }
        return productCategoryToCampaigns;
    }
}

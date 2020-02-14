package com.trendyol.shoppingcart.rules;

import com.trendyol.shoppingcart.Campaign;
import com.trendyol.shoppingcart.CampaignSelectionRule;
import com.trendyol.shoppingcart.Category;
import com.trendyol.shoppingcart.ShoppingCart;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class ChooseCampaignOfNearestCategoryInParentsIfNoExactlyMatchedCampaign implements CampaignSelectionRule {

    @Override
    public Map<Category, List<Campaign>> apply(Map<Category, List<Campaign>> campaignMap, ShoppingCart shoppingCart) {

        Map<Category, List<Campaign>> productCategoryToCampaigns = new HashMap<>();

        for (Map.Entry<Category, List<Campaign>> entry : campaignMap.entrySet()) {

            List<Category> campaignCategories = entry.getValue().stream().map(c->c.getCategory()).collect(toList());
            Category productCategory = entry.getKey();
            Optional<Campaign> campaignSelected = productCategory.getCategoriesBottomToTopOrdered()
                                                                 .stream()
                                                                 .filter(c->campaignCategories.contains(c))
                                                                 .findFirst()
                                                                 .map(c->entry.getValue().get(campaignCategories.indexOf(c)));

            campaignSelected.ifPresent(c->productCategoryToCampaigns.put(entry.getKey(), Arrays.asList(c)));
        }

        return productCategoryToCampaigns;
    }
}

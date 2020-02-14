package com.trendyol.shoppingcart.rules;

import com.trendyol.shoppingcart.Campaign;
import com.trendyol.shoppingcart.CampaignSelectionRule;
import com.trendyol.shoppingcart.Category;
import com.trendyol.shoppingcart.ShoppingCart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class ChooseMaxDiscountedCampaigns implements CampaignSelectionRule {

    @Override
    public Map<Category, List<Campaign>> apply(Map<Category, List<Campaign>> campaignList, ShoppingCart shoppingCart) {

        Map<Category, List<Campaign>> productCategoryToCampaigns = new HashMap<>();

        for (Map.Entry<Category, List<Campaign>> entry : campaignList.entrySet()) {

            Map<Category, Campaign> mapToCampaignWithMaxDiscount = entry.getValue()
                                                                        .stream()
                                                                        .collect(toMap(c->c.getCategory(),
                                                                                       Function.identity(),
                                                                                       (Campaign c1, Campaign c2) -> c1.getDiscount().getValue()>c2.getDiscount().getValue()?c1:c2));
            productCategoryToCampaigns.put(entry.getKey(), new ArrayList<>(mapToCampaignWithMaxDiscount.values()));
        }

        return productCategoryToCampaigns;
    }
}

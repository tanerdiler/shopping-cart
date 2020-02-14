package com.trendyol.shoppingcart;

import com.trendyol.shoppingcart.rules.ChooseCampaignOfNearestCategoryInParentsIfNoExactlyMatchedCampaign;
import com.trendyol.shoppingcart.rules.ChooseCategoryGraphMatchedCampaigns;
import com.trendyol.shoppingcart.rules.ChooseLimitMatchedCampaigns;
import com.trendyol.shoppingcart.rules.ChooseMaxDiscountedCampaigns;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface CampaignSelectionRule {

    Map<Category, List<Campaign>> apply(Map<Category, List<Campaign>> campaignMap, ShoppingCart shoppingCart);

    static List<CampaignSelectionRule> getRules() {
        return Arrays.asList(new ChooseCategoryGraphMatchedCampaigns(),
                             new ChooseLimitMatchedCampaigns(),
                             new ChooseMaxDiscountedCampaigns(),
                             new ChooseCampaignOfNearestCategoryInParentsIfNoExactlyMatchedCampaign());
    }
}

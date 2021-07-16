package com.syte.ai.android_sdk.data.result.recommendation;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.offers.Offer;

public class ShopTheLookResult {

    private AccountDataService accountDataService;

    @SerializedName("response")
    private List<ShopTheLookResponseItem> items;

    @SerializedName("fallback")
    private String fallback;

    public List<ShopTheLookResponseItem> getItems() {
        return items;
    }

    public String getFallback() {
        return fallback;
    }

    public void setAccountDataService(AccountDataService accountDataService) {
        this.accountDataService = accountDataService;
    }

    public List<Offer> getAllOffers() {
        if (accountDataService == null) {
            return getAllOffers(false);
        }
        return getAllOffers(accountDataService
                .getData()
                .getProducts()
                .getSyteapp()
                .getFeatures()
                .getShopTheLook()
                .isZip());
    }

    public List<Offer> getAllOffers(boolean forceZip) {
        List<Offer> offerList = new ArrayList<>();
        if (forceZip) {
            int maxIdx = 0;

            for (ShopTheLookResponseItem item : items) {
                if (item.getOffers().size() - 1 > maxIdx) {
                    maxIdx = item.getOffers().size() - 1;
                }
            }

            for (int i = 0; i <= maxIdx; i++) {
                for (ShopTheLookResponseItem item : items) {
                    if (item.getOffers().size() > i) {
                        offerList.add(item.getOffers().get(i));
                    }
                }
            }

            return offerList;
        }

        for (ShopTheLookResponseItem item : items) {
            offerList.addAll(item.getOffers());
        }

        return offerList;
    }

    @Override
    public String toString() {
        return
                "ShopTheLookResult{" +
                        "response = '" + items + '\'' +
                        ",fallback = '" + fallback + '\'' +
                        "}";
    }
}
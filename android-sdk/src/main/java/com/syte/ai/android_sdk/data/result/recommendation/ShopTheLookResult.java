package com.syte.ai.android_sdk.data.result.recommendation;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.data.result.offers.Offer;

/**
 * A class that represents the result for 'Shop the look' call
 */
public class ShopTheLookResult {

    private SytePlatformSettings sytePlatformSettings;

    @SerializedName("response")
    private List<ShopTheLookResponseItem> items;

    @SerializedName("fallback")
    private String fallback;

    /**
     * Returns the list of {@link ShopTheLookResponseItem}
     * @return list of {@link ShopTheLookResponseItem}
     */
    public List<ShopTheLookResponseItem> getItems() {
        return items;
    }

    /**
     * Getter for fallback value
     * @return fallback
     */
    public String getFallback() {
        return fallback;
    }

    /**
     * Setter for {@link SytePlatformSettings}. Is used internally
     * @param sytePlatformSettings {@link SytePlatformSettings}
     */
    public void setSytePlatformSettings(SytePlatformSettings sytePlatformSettings) {
        this.sytePlatformSettings = sytePlatformSettings;
    }

    /**
     * Get list of all retrieved items. If zip in {@link SytePlatformSettings} is true, the items will be shuffled.
     * @return list of all retrieved items
     */
    public List<Offer> getAllItems() {
        if (sytePlatformSettings == null) {
            return getAllItems(false);
        }
        return getAllItems(sytePlatformSettings
                .getData()
                .getProducts()
                .getSyteapp()
                .getFeatures()
                .getShopTheLook()
                .isZip());
    }

    /**
     * Get list of all retrieved items.
     * @param forceZip - true to shuffle items (disregarding the zip value in {@link SytePlatformSettings})
     * @return list of all retrieved items
     */
    public List<Offer> getAllItems(boolean forceZip) {
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

}
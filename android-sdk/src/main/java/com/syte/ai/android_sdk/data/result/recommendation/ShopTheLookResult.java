package com.syte.ai.android_sdk.data.result.recommendation;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.data.result.offers.Item;

/**
 * A class that represents the result for 'Shop the look' call
 */
public class ShopTheLookResult {

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
     * Get list of all retrieved items.
     * @param forceZip - true to shuffle items)
     * @return list of all retrieved items
     */
    public List<Item> getItemsForAllLabels(boolean forceZip) {
        List<Item> itemList = new ArrayList<>();
        if (forceZip) {
            int maxIdx = 0;

            for (ShopTheLookResponseItem item : items) {
                if (item.getItems().size() - 1 > maxIdx) {
                    maxIdx = item.getItems().size() - 1;
                }
            }

            for (int i = 0; i <= maxIdx; i++) {
                for (ShopTheLookResponseItem item : items) {
                    if (item.getItems().size() > i) {
                        itemList.add(item.getItems().get(i));
                    }
                }
            }

            return itemList;
        }

        for (ShopTheLookResponseItem item : items) {
            itemList.addAll(item.getItems());
        }

        return itemList;
    }

}
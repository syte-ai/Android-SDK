package com.syte.ai.android_sdk.data.result.offers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * A class that represents the return result for the offers requests.
 */
public class ItemsResult {

    @SerializedName("ads")
    private List<Item> items = new ArrayList<>();

    @SerializedName("currency_symbol")
    private String currencySymbol;

    @SerializedName("currency_tla")
    private String currencyTla;

    /**
     * Getter for the list of items.
     * @return list of items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * Getter for currency symbol
     * @return currency symbol
     */
    public String getCurrencySymbol() {
        return currencySymbol;
    }

    /**
     * Getter for currency Tla
     * @return currency Tla
     */
    public String getCurrencyTla() {
        return currencyTla;
    }

}
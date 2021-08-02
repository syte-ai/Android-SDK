package com.syte.ai.android_sdk.data.result.offers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * A class that represents the return result for the offers requests.
 */
public class OffersResult {

    @SerializedName("ads")
    private List<Offer> offers = new ArrayList<>();

    @SerializedName("currency_symbol")
    private String currencySymbol;

    @SerializedName("currency_tla")
    private String currencyTla;

    /**
     * Getter for the list of offers.
     * @return list of offers
     */
    public List<Offer> getOffers() {
        return offers;
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
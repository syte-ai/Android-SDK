package com.syte.ai.android_sdk.data.result.offers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class OffersResult {

    @SerializedName("ads")
    private List<Offer> offers = new ArrayList<>();

    @SerializedName("currency_symbol")
    private String currencySymbol;

    @SerializedName("currency_tla")
    private String currencyTla;

    public List<Offer> getOffers() {
        return offers;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getCurrencyTla() {
        return currencyTla;
    }

    @Override
    public String toString() {
        return
                "OffersResult{" +
                        "currency_symbol = '" + currencySymbol + '\'' +
                        ",currency_tla = '" + currencyTla + '\'' +
                        ",ads = '" + offers + '\'' +
                        "}";
    }
}
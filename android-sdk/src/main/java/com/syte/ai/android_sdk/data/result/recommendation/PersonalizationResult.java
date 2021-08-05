package com.syte.ai.android_sdk.data.result.recommendation;

import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.result.offers.Offer;

import java.util.List;

/**
 * A class that represents the result for Personalization call
 */
public class PersonalizationResult {

    @SerializedName("results")
    private List<Offer> data;

    /**
     * Getter for retrieved items
     * @return items
     */
    public List<Offer> getItems(){
        return data;
    }

}

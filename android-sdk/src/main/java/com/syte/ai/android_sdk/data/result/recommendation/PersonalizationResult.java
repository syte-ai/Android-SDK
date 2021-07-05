package com.syte.ai.android_sdk.data.result.recommendation;

import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.result.offers.Offer;

import java.util.List;

public class PersonalizationResult {

    @SerializedName("results")
    private List<Offer> data;

    public List<Offer> getItems(){
        return data;
    }

    @Override
    public String toString(){
        return
                "SimilarProductsResult{" +
                        "data = '" + data + '\'' +
                        "}";
    }

}

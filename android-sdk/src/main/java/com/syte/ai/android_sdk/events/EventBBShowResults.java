package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

public class EventBBShowResults extends BaseSyteEvent {

    @SerializedName("image_url")
    private String mImageUrl;

    @SerializedName("category")
    private String mCategory;

    @SerializedName("results_count")
    private int mResultsCount;

    public EventBBShowResults(
            String imageUrl,
            String category,
            int resultsCount,
            String syteUrlReferer) {
        super("fe_bb_show_results", syteUrlReferer, EventsTag.CAMERA);
        mCategory = category;
        mImageUrl = imageUrl;
        mResultsCount = resultsCount;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventBBShowResults.class);
    }

}

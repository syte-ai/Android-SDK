package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * This event should be sent to Syte every time a results for specific bound from the image are shown
 */
public class EventBBShowResults extends BaseSyteEvent {

    @SerializedName("image_url")
    private String mImageUrl;

    @SerializedName("category")
    private String mCategory;

    @SerializedName("results_count")
    private int mResultsCount;

    /**
     * @param imageUrl Url of image used
     * @param category The bound category as returned in the bounds request
     * @param resultsCount The number of returned results in the offers request
     * @param pageName unique page name given by the app developer
     */
    public EventBBShowResults(
            String imageUrl,
            String category,
            int resultsCount,
            String pageName) {
        super("fe_bb_show_results", pageName, EventsTag.CAMERA);
        mCategory = category;
        mImageUrl = imageUrl;
        mResultsCount = resultsCount;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventBBShowResults.class);
    }

}

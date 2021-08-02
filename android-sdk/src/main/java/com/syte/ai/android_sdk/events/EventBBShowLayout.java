package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * Default Syte event
 */
public class EventBBShowLayout extends BaseSyteEvent {

    @SerializedName("image_url")
    private String mImageUrl;

    @SerializedName("num_of_bbs")
    private int mNumOfBBs;

    public EventBBShowLayout(
            String imageUrl,
            int numOfBBs,
            String syteUrlReferer) {
        super("fe_bb_show_layout", syteUrlReferer, EventsTag.CAMERA);
        mImageUrl = imageUrl;
        mNumOfBBs = numOfBBs;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventBBShowLayout.class);
    }

}

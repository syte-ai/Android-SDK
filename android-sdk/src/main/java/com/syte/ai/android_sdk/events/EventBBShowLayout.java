package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * This event should be sent to Syte every time a Bounding Boxes layout is visible for user after fetching bounds for an image
 */
public class EventBBShowLayout extends BaseSyteEvent {

    @SerializedName("image_url")
    private String mImageUrl;

    @SerializedName("num_of_bbs")
    private int mNumOfBBs;

    /**
     * @param imageUrl Url of image used
     * @param numOfBBs the number of bounds returned from bounds request
     * @param pageName unique page name given by the app developer
     */
    public EventBBShowLayout(
            String imageUrl,
            int numOfBBs,
            String pageName) {
        super("fe_bb_show_layout", pageName, EventsTag.CAMERA);
        mImageUrl = imageUrl;
        mNumOfBBs = numOfBBs;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventBBShowLayout.class);
    }

}

package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.Catalog;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * Default Syte event
 */
public class EventBBClick extends BaseSyteEvent {

    @SerializedName("image_url")
    private String mImageUrl;

    @SerializedName("category")
    private String mCategory;

    @SerializedName("gender")
    private String mGender;

    @SerializedName("catalog")
    private String mCatalog;

    public EventBBClick(
            String imageUrl,
            String category,
            String gender,
            String catalog,
            String syteUrlReferer) {
        super("fe_bb_bb_click", syteUrlReferer, EventsTag.CAMERA);
        mImageUrl = imageUrl;
        mCatalog = catalog;
        mGender = gender;
        mCategory = category;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventBBClick.class);
    }

}

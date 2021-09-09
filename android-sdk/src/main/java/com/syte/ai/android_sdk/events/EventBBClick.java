package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.Catalog;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * This event should be sent to Syte every time a bound selected
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

    /**
     * @param imageUrl Url of image used
     * @param category The bound category as returned in the bounds request
     * @param gender The bounds gender as returned by Syte
     * @param catalog The selected bound catalog. See {@link Catalog}
     * @param pageName unique page name given by the app developer
     */
    public EventBBClick(
            String imageUrl,
            String category,
            String gender,
            String catalog,
            String pageName) {
        super("fe_bb_bb_click", pageName, EventsTag.CAMERA);
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

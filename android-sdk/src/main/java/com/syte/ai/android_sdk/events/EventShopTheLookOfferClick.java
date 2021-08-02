package com.syte.ai.android_sdk.events;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.enums.EventsTag;

/**
 * Default Syte event
 */
public class EventShopTheLookOfferClick extends BaseSyteEvent {

    @SerializedName("sku")
    private final String mSku;

    @SerializedName("position")
    private final int mPosition;

    public EventShopTheLookOfferClick(
            String sku,
            int position,
            String syteUrlReferer
    ) {
        super("fe_offer_click", syteUrlReferer, EventsTag.SHOP_THE_LOOK);
        mPosition = position;
        mSku = sku;
    }

    @Override
    public String getRequestBodyString() {
        return new Gson().toJson(this, EventShopTheLookOfferClick.class);
    }

}

package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;

public interface ImageSearchClient {
    
    SyteResult<BoundsResult> getBounds(ImageSearchRequestData imageSearchRequestData);

    SyteResult<BoundsResult> getBounds(UrlImageSearchRequestData urlImageSearchRequestData);

    SyteResult<OffersResult> getOffers(Bound bound);

    void getBoundsAsync(ImageSearchRequestData imageSearchRequestData, SyteCallback<BoundsResult> callback);

    void getBoundsAsync(UrlImageSearchRequestData urlImageSearchRequestData, SyteCallback<BoundsResult> callback);

    void getOffersAsync(Bound bound, SyteCallback<OffersResult> callback);

}

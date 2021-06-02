package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;

public interface ImageSearchClient {
    
    SyteResult launch(ImageSearchRequestData imageSearchRequestData);

    SyteResult launch(UrlImageSearchRequestData urlImageSearchRequestData);

    void launchAsync(ImageSearchRequestData imageSearchRequestData, SyteCallback callback);

    void launchAsync(UrlImageSearchRequestData urlImageSearchRequestData, SyteCallback callback);

}

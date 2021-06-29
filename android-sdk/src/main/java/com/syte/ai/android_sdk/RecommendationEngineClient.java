package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.RecommendationRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;

public interface RecommendationEngineClient {

    //TODO: proper use of generics
    SyteResult launch(RecommendationRequestData recommendationRequestData);

    //TODO: proper use of generics
    void launchAsync(RecommendationRequestData recommendationRequestData, SyteCallback callback);

}

package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.RecommendationRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;

public interface RecommendationEngineClient {

    SyteResult launch(RecommendationRequestData recommendationRequestData);

    void launchAsync(RecommendationRequestData recommendationRequestData, SyteCallback callback);

}

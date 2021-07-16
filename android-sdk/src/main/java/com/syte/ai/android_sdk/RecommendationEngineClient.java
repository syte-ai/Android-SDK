package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.data.ShopTheLookRequestData;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;

public interface RecommendationEngineClient {

    SyteResult<SimilarProductsResult> getSimilarProducts(
            SimilarProductsRequestData similarProductsRequestData
    );

    void getSimilarProductsAsync(
            SimilarProductsRequestData similarProductsRequestData,
            SyteCallback<SimilarProductsResult> callback
    );

    SyteResult<ShopTheLookResult> getShopTheLook(
            ShopTheLookRequestData shopTheLookRequestData
    );

    void getShopTheLookAsync(
            ShopTheLookRequestData shopTheLookRequestData,
            SyteCallback<ShopTheLookResult> callback
    );

    SyteResult<PersonalizationResult> getPersonalization(
            PersonalizationRequestData personalizationRequestData
    );

    void getPersonalizationAsync(
            PersonalizationRequestData personalizationRequestData,
            SyteCallback<PersonalizationResult> callback
    );

}

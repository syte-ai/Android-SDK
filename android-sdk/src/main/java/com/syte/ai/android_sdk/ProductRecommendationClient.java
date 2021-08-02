package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.data.ShopTheLookRequestData;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;

/**
 * Class responsible for Recommendation functionality.
 */
public interface ProductRecommendationClient {

    /**
     * Make 'Similars' call
     * Synchronous method. Must NOT be called on the UI thread!
     * @param similarProductsRequestData {@link SimilarProductsRequestData}
     * @return {@link SyteResult}
     */
    SyteResult<SimilarProductsResult> getSimilarProducts(
            SimilarProductsRequestData similarProductsRequestData
    );

    /**
     * Make 'Similars' call
     * Asynchronous equivalent for the {@link #getSimilarProducts(SimilarProductsRequestData)} method.
     * @param similarProductsRequestData {@link SimilarProductsRequestData}
     * @param callback {@link SyteCallback}
     */
    void getSimilarProductsAsync(
            SimilarProductsRequestData similarProductsRequestData,
            SyteCallback<SimilarProductsResult> callback
    );

    /**
     * Make 'Shop the look' call
     * Synchronous method. Must NOT be called on the UI thread!
     * @param shopTheLookRequestData {@link ShopTheLookRequestData}
     * @return {@link SyteResult}
     */
    SyteResult<ShopTheLookResult> getShopTheLook(
            ShopTheLookRequestData shopTheLookRequestData
    );

    /**
     * Make 'Shop the look' call
     * Asynchronous equivalent for the {@link #getShopTheLook(ShopTheLookRequestData)} method.
     * @param shopTheLookRequestData {@link ShopTheLookRequestData}
     * @param callback {@link SyteCallback}
     */
    void getShopTheLookAsync(
            ShopTheLookRequestData shopTheLookRequestData,
            SyteCallback<ShopTheLookResult> callback
    );

    /**
     * Make 'Personalization' call
     * Synchronous method. Must NOT be called on the UI thread!
     * @param personalizationRequestData {@link PersonalizationRequestData}
     * @return {@link SyteResult}
     */
    SyteResult<PersonalizationResult> getPersonalization(
            PersonalizationRequestData personalizationRequestData
    );

    /**
     * Make 'Personalization' call
     * Asynchronous equivalent for the {@link #getPersonalization(PersonalizationRequestData)} method.
     * @param personalizationRequestData {@link PersonalizationRequestData}
     * @param callback {@link SyteCallback}
     */
    void getPersonalizationAsync(
            PersonalizationRequestData personalizationRequestData,
            SyteCallback<PersonalizationResult> callback
    );

}

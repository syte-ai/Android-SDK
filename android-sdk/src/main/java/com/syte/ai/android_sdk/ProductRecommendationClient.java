package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.Personalization;
import com.syte.ai.android_sdk.data.ShopTheLook;
import com.syte.ai.android_sdk.data.SimilarProducts;
import com.syte.ai.android_sdk.data.result.SyteResult;
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
     * @param similarProducts {@link SimilarProducts}
     * @return {@link SyteResult}
     */
    SyteResult<SimilarProductsResult> getSimilarProducts(
            SimilarProducts similarProducts
    );

    /**
     * Make 'Similars' call
     * Asynchronous equivalent for the {@link #getSimilarProducts(SimilarProducts)} method.
     * @param similarProducts {@link SimilarProducts}
     * @param callback {@link SyteCallback}
     */
    void getSimilarProductsAsync(
            SimilarProducts similarProducts,
            SyteCallback<SimilarProductsResult> callback
    );

    /**
     * Make 'Shop the look' call
     * Synchronous method. Must NOT be called on the UI thread!
     * @param shopTheLook {@link ShopTheLook}
     * @return {@link SyteResult}
     */
    SyteResult<ShopTheLookResult> getShopTheLook(
            ShopTheLook shopTheLook
    );

    /**
     * Make 'Shop the look' call
     * Asynchronous equivalent for the {@link #getShopTheLook(ShopTheLook)} method.
     * @param shopTheLook {@link ShopTheLook}
     * @param callback {@link SyteCallback}
     */
    void getShopTheLookAsync(
            ShopTheLook shopTheLook,
            SyteCallback<ShopTheLookResult> callback
    );

    /**
     * Make 'Personalization' call
     * Synchronous method. Must NOT be called on the UI thread!
     * @param personalization {@link Personalization}
     * @return {@link SyteResult}
     */
    SyteResult<PersonalizationResult> getPersonalizedProducts(
            Personalization personalization
    );

    /**
     * Make 'Personalization' call
     * Asynchronous equivalent for the {@link #getPersonalizedProducts(Personalization)} method.
     * @param personalization {@link Personalization}
     * @param callback {@link SyteCallback}
     */
    void getPersonalizedProductsAsync(
            Personalization personalization,
            SyteCallback<PersonalizationResult> callback
    );

}

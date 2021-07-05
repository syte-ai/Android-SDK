package com.syte.ai.android_sdk.core;


import com.syte.ai.android_sdk.RecommendationEngineClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.data.ShopTheLookRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;

class RecommendationEngineClientImpl implements RecommendationEngineClient {

    private final SyteRemoteDataSource mSyteRemoteDataSource;
    private final AccountDataService mAccountDataService;

    RecommendationEngineClientImpl(
            SyteRemoteDataSource syteRemoteDataSource,
            AccountDataService accountDataService
    ) {
        mSyteRemoteDataSource = syteRemoteDataSource;
        mAccountDataService = accountDataService;
    }

    @Override
    public SyteResult<SimilarProductsResult> getSimilarProducts(
            SimilarProductsRequestData similarProductsRequestData
    ) {
        return mSyteRemoteDataSource.getSimilarProducts(similarProductsRequestData);
    }

    @Override
    public void getSimilarProductsAsync(
            SimilarProductsRequestData similarProductsRequestData,
            SyteCallback<SimilarProductsResult> callback
    ) {
        mSyteRemoteDataSource.getSimilarProductsAsync(
                similarProductsRequestData,
                new SyteCallback<SimilarProductsResult>() {
                    @Override
                    public void onResult(SyteResult<SimilarProductsResult> syteResult) {
                        callback.onResult(syteResult);
                    }
                }
        );
    }

    @Override
    public SyteResult<ShopTheLookResult> getShopTheLook(
            ShopTheLookRequestData shopTheLookRequestData
    ) {
        return mSyteRemoteDataSource.getShopTheLook(shopTheLookRequestData);
    }

    @Override
    public void getShopTheLookAsync(
            ShopTheLookRequestData shopTheLookRequestData,
            SyteCallback<ShopTheLookResult> callback
    ) {
        mSyteRemoteDataSource.getShopTheLookAsync(
                shopTheLookRequestData,
                new SyteCallback<ShopTheLookResult>() {
                    @Override
                    public void onResult(SyteResult<ShopTheLookResult> syteResult) {
                        callback.onResult(syteResult);
                    }
                }
        );
    }

    @Override
    public SyteResult<PersonalizationResult> getPersonalization(
            PersonalizationRequestData personalizationRequestData
    ) {
        return mSyteRemoteDataSource.getPersonalization(personalizationRequestData);
    }

    @Override
    public void getPersonalizationAsync(
            PersonalizationRequestData personalizationRequestData,
            SyteCallback<PersonalizationResult> callback
    ) {
        mSyteRemoteDataSource.getPersonalizationAsync(
                personalizationRequestData,
                new SyteCallback<PersonalizationResult>() {
                    @Override
                    public void onResult(SyteResult<PersonalizationResult> syteResult) {
                        callback.onResult(syteResult);
                    }
                }
        );
    }

}

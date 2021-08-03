package com.syte.ai.android_sdk.core;


import com.syte.ai.android_sdk.ProductRecommendationClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.data.ShopTheLookRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

class ProductRecommendationClientImpl implements ProductRecommendationClient {

    private final SyteRemoteDataSource mSyteRemoteDataSource;
    private final SytePlatformSettings mSytePlatformSettings;

    ProductRecommendationClientImpl(
            SyteRemoteDataSource syteRemoteDataSource,
            SytePlatformSettings sytePlatformSettings
    ) {
        mSyteRemoteDataSource = syteRemoteDataSource;
        mSytePlatformSettings = sytePlatformSettings;
    }

    @Override
    public SyteResult<SimilarProductsResult> getSimilarProducts(
            SimilarProductsRequestData similarProductsRequestData
    ) {
        try {
            InputValidator.validateInput(similarProductsRequestData);
        } catch (SyteWrongInputException e) {
            SyteResult<SimilarProductsResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }
        return mSyteRemoteDataSource.getSimilarProducts(similarProductsRequestData);
    }

    @Override
    public void getSimilarProductsAsync(
            SimilarProductsRequestData similarProductsRequestData,
            SyteCallback<SimilarProductsResult> callback
    ) {
        try {
            InputValidator.validateInput(similarProductsRequestData);
        } catch (SyteWrongInputException e) {
            if (callback != null) {
                SyteResult<SimilarProductsResult> result = new SyteResult<>();
                result.errorMessage = e.getMessage();
                callback.onResult(result);
            }
            return;
        }
        mSyteRemoteDataSource.getSimilarProductsAsync(
                similarProductsRequestData,
                new SyteCallback<SimilarProductsResult>() {
                    @Override
                    public void onResult(SyteResult<SimilarProductsResult> syteResult) {
                        if (callback != null) {
                            callback.onResult(syteResult);
                        }
                    }
                }
        );
    }

    @Override
    public SyteResult<ShopTheLookResult> getShopTheLook(
            ShopTheLookRequestData shopTheLookRequestData
    ) {
        try {
            InputValidator.validateInput(shopTheLookRequestData);
        } catch (SyteWrongInputException e) {
            SyteResult<ShopTheLookResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }
        return mSyteRemoteDataSource.getShopTheLook(shopTheLookRequestData, mSytePlatformSettings);
    }

    @Override
    public void getShopTheLookAsync(
            ShopTheLookRequestData shopTheLookRequestData,
            SyteCallback<ShopTheLookResult> callback
    ) {
        try {
            InputValidator.validateInput(shopTheLookRequestData);
        } catch (SyteWrongInputException e) {
            SyteResult<ShopTheLookResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            if (callback != null) {
                callback.onResult(result);
            }
            return;
        }
        mSyteRemoteDataSource.getShopTheLookAsync(
                shopTheLookRequestData,
                mSytePlatformSettings,
                new SyteCallback<ShopTheLookResult>() {
                    @Override
                    public void onResult(SyteResult<ShopTheLookResult> syteResult) {
                        if (callback != null) {
                            callback.onResult(syteResult);
                        }
                    }
                }
        );
    }

    @Override
    public SyteResult<PersonalizationResult> getPersonalization(
            PersonalizationRequestData personalizationRequestData
    ) {
        try {
            InputValidator.validateInput(personalizationRequestData);
        } catch (SyteWrongInputException e) {
            SyteResult<PersonalizationResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }
        return mSyteRemoteDataSource.getPersonalization(personalizationRequestData);
    }

    @Override
    public void getPersonalizationAsync(
            PersonalizationRequestData personalizationRequestData,
            SyteCallback<PersonalizationResult> callback
    ) {
        try {
            InputValidator.validateInput(personalizationRequestData);
        } catch (SyteWrongInputException e) {
            SyteResult<PersonalizationResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            if (callback != null) {
                callback.onResult(result);
            }
            return;
        }
        mSyteRemoteDataSource.getPersonalizationAsync(
                personalizationRequestData,
                new SyteCallback<PersonalizationResult>() {
                    @Override
                    public void onResult(SyteResult<PersonalizationResult> syteResult) {
                        if (callback != null) {
                            callback.onResult(syteResult);
                        }
                    }
                }
        );
    }

}
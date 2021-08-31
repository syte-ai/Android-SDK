package com.syte.ai.android_sdk.core;


import com.syte.ai.android_sdk.ProductRecommendationClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.Personalization;
import com.syte.ai.android_sdk.data.ShopTheLook;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.SimilarItems;
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
            SimilarItems similarProducts
    ) {
        try {
            InputValidator.validateInput(similarProducts);
        } catch (SyteWrongInputException e) {
            SyteResult<SimilarProductsResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }
        return mSyteRemoteDataSource.getSimilarProducts(similarProducts);
    }

    @Override
    public void getSimilarProductsAsync(
            SimilarItems similarProducts,
            SyteCallback<SimilarProductsResult> callback
    ) {
        try {
            InputValidator.validateInput(similarProducts);
        } catch (SyteWrongInputException e) {
            if (callback != null) {
                SyteResult<SimilarProductsResult> result = new SyteResult<>();
                result.errorMessage = e.getMessage();
                callback.onResult(result);
            }
            return;
        }
        mSyteRemoteDataSource.getSimilarProductsAsync(
                similarProducts,
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
            ShopTheLook shopTheLook
    ) {
        try {
            InputValidator.validateInput(shopTheLook);
        } catch (SyteWrongInputException e) {
            SyteResult<ShopTheLookResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }
        return mSyteRemoteDataSource.getShopTheLook(shopTheLook, mSytePlatformSettings);
    }

    @Override
    public void getShopTheLookAsync(
            ShopTheLook shopTheLook,
            SyteCallback<ShopTheLookResult> callback
    ) {
        try {
            InputValidator.validateInput(shopTheLook);
        } catch (SyteWrongInputException e) {
            SyteResult<ShopTheLookResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            if (callback != null) {
                callback.onResult(result);
            }
            return;
        }
        mSyteRemoteDataSource.getShopTheLookAsync(
                shopTheLook,
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
    public SyteResult<PersonalizationResult> getPersonalizedProducts(
            Personalization personalization
    ) {
        try {
            InputValidator.validateInput(personalization);
        } catch (SyteWrongInputException e) {
            SyteResult<PersonalizationResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }
        return mSyteRemoteDataSource.getPersonalization(personalization);
    }

    @Override
    public void getPersonalizedProductsAsync(
            Personalization personalization,
            SyteCallback<PersonalizationResult> callback
    ) {
        try {
            InputValidator.validateInput(personalization);
        } catch (SyteWrongInputException e) {
            SyteResult<PersonalizationResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            if (callback != null) {
                callback.onResult(result);
            }
            return;
        }
        mSyteRemoteDataSource.getPersonalizationAsync(
                personalization,
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

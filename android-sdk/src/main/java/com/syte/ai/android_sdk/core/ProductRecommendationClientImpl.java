package com.syte.ai.android_sdk.core;


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

class ProductRecommendationClientImpl {

    private final SyteRemoteDataSource mSyteRemoteDataSource;

    ProductRecommendationClientImpl(
            SyteRemoteDataSource syteRemoteDataSource
    ) {
        mSyteRemoteDataSource = syteRemoteDataSource;
    }

    public SyteResult<SimilarProductsResult> getSimilarProducts(
            SimilarItems similarProducts
    ) {
        try {
            InputValidator.validateInput(similarProducts);
        } catch (SyteWrongInputException e) {
            return mSyteRemoteDataSource.handleOnFailure(e);
        }
        return mSyteRemoteDataSource.getSimilarProducts(similarProducts);
    }

    public void getSimilarProductsAsync(
            SimilarItems similarProducts,
            SyteCallback<SimilarProductsResult> callback
    ) {
        try {
            InputValidator.validateInput(similarProducts);
        } catch (SyteWrongInputException e) {
            if (callback != null) {
                callback.onResult(mSyteRemoteDataSource.handleOnFailure(e));
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

    public SyteResult<ShopTheLookResult> getShopTheLook(
            ShopTheLook shopTheLook
    ) {
        try {
            InputValidator.validateInput(shopTheLook);
        } catch (SyteWrongInputException e) {
            return mSyteRemoteDataSource.handleOnFailure(e);
        }
        return mSyteRemoteDataSource.getShopTheLook(shopTheLook);
    }

    public void getShopTheLookAsync(
            ShopTheLook shopTheLook,
            SyteCallback<ShopTheLookResult> callback
    ) {
        try {
            InputValidator.validateInput(shopTheLook);
        } catch (SyteWrongInputException e) {
            if (callback != null) {
                callback.onResult(mSyteRemoteDataSource.handleOnFailure(e));
            }
            return;
        }
        mSyteRemoteDataSource.getShopTheLookAsync(
                shopTheLook,
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

    public SyteResult<PersonalizationResult> getPersonalizedProducts(
            Personalization personalization
    ) {
        try {
            InputValidator.validateInput(personalization);
        } catch (SyteWrongInputException e) {
            return mSyteRemoteDataSource.handleOnFailure(e);
        }
        return mSyteRemoteDataSource.getPersonalization(personalization);
    }

    public void getPersonalizedProductsAsync(
            Personalization personalization,
            SyteCallback<PersonalizationResult> callback
    ) {
        try {
            InputValidator.validateInput(personalization);
        } catch (SyteWrongInputException e) {
            if (callback != null) {
                callback.onResult(mSyteRemoteDataSource.handleOnFailure(e));
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

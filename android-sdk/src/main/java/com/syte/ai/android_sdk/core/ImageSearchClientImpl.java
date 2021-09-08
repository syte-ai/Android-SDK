package com.syte.ai.android_sdk.core;

import android.content.Context;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.ImageSearch;
import com.syte.ai.android_sdk.data.UrlImageSearch;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.ItemsResult;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

class ImageSearchClientImpl {

    private final SyteRemoteDataSource mSyteRemoteDataSource;
    private final SytePlatformSettings mSytePlatformSettings;

    ImageSearchClientImpl(SyteRemoteDataSource syteRemoteDataSource, SytePlatformSettings sytePlatformSettings) {
        mSyteRemoteDataSource = syteRemoteDataSource;
        mSytePlatformSettings = sytePlatformSettings;
    }

    public SyteResult<BoundsResult> getBounds(Context context, ImageSearch imageSearch) {
        try {
            InputValidator.validateInput(imageSearch);
            InputValidator.validateInput(context);
        } catch (SyteWrongInputException e) {
            SyteResult<BoundsResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }

        return mSyteRemoteDataSource.getBoundsWild(
                context,
                imageSearch,
                mSytePlatformSettings
        );
    }

    public SyteResult<BoundsResult> getBounds(UrlImageSearch urlImageSearch) {
        try {
            InputValidator.validateInput(urlImageSearch);
        } catch (SyteWrongInputException e) {
            SyteResult<BoundsResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }
        return mSyteRemoteDataSource.getBounds(urlImageSearch, mSytePlatformSettings);
    }

    public SyteResult<ItemsResult> getItemsForBound(Bound bound, CropCoordinates cropCoordinates) {
        try {
            InputValidator.validateInput(bound);
        } catch (SyteWrongInputException e) {
            SyteResult<ItemsResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            return result;
        }
        return mSyteRemoteDataSource.getOffers(bound, cropCoordinates, mSytePlatformSettings);
    }

    public void getItemsForBoundAsync(Bound bound, CropCoordinates cropCoordinates, SyteCallback<ItemsResult> callback) {
        try {
            InputValidator.validateInput(bound);
        } catch (SyteWrongInputException e) {
            SyteResult<ItemsResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            if (callback != null) {
                callback.onResult(result);
            }
            return;
        }
        mSyteRemoteDataSource.getOffersAsync(bound, cropCoordinates, mSytePlatformSettings, callback);
    }

    public void getBoundsAsync(
            Context context,
            ImageSearch imageSearch,
            SyteCallback<BoundsResult> callback
    ) {
        try {
            InputValidator.validateInput(context);
            InputValidator.validateInput(imageSearch);
        } catch (SyteWrongInputException e) {
            SyteResult<BoundsResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            if (callback != null) {
                callback.onResult(result);
            }
            return;
        }
        mSyteRemoteDataSource.getBoundsWildAsync(context, imageSearch, mSytePlatformSettings, callback);
    }

    public void getBoundsAsync(UrlImageSearch urlImageSearch, SyteCallback<BoundsResult> callback) {
        try {
            InputValidator.validateInput(urlImageSearch);
        } catch (SyteWrongInputException e) {
            SyteResult<BoundsResult> result = new SyteResult<>();
            result.errorMessage = e.getMessage();
            if (callback != null) {
                callback.onResult(result);
            }
            return;
        }
        mSyteRemoteDataSource.getBoundsAsync(urlImageSearch, mSytePlatformSettings, callback);
    }

}

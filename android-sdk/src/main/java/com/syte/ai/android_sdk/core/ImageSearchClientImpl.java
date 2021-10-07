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

    ImageSearchClientImpl(SyteRemoteDataSource syteRemoteDataSource) {
        mSyteRemoteDataSource = syteRemoteDataSource;
    }

    public SyteResult<BoundsResult> getBounds(Context context, ImageSearch imageSearch) {
        try {
            InputValidator.validateInput(imageSearch);
            InputValidator.validateInput(context);
        } catch (SyteWrongInputException e) {
            return mSyteRemoteDataSource.handleOnFailure(e);
        }

        return mSyteRemoteDataSource.getBoundsWild(
                context,
                imageSearch
        );
    }

    public SyteResult<BoundsResult> getBounds(UrlImageSearch urlImageSearch) {
        try {
            InputValidator.validateInput(urlImageSearch);
        } catch (SyteWrongInputException e) {
            return mSyteRemoteDataSource.handleOnFailure(e);
        }
        return mSyteRemoteDataSource.getBounds(urlImageSearch);
    }

    public SyteResult<ItemsResult> getItemsForBound(Bound bound, CropCoordinates cropCoordinates) {
        try {
            InputValidator.validateInput(bound);
        } catch (SyteWrongInputException e) {
            return mSyteRemoteDataSource.handleOnFailure(e);
        }
        return mSyteRemoteDataSource.getOffers(bound, cropCoordinates);
    }

    public void getItemsForBoundAsync(Bound bound, CropCoordinates cropCoordinates, SyteCallback<ItemsResult> callback) {
        try {
            InputValidator.validateInput(bound);
        } catch (SyteWrongInputException e) {
            if (callback != null) {
                callback.onResult(mSyteRemoteDataSource.handleOnFailure(e));
            }
            return;
        }
        mSyteRemoteDataSource.getOffersAsync(bound, cropCoordinates, callback);
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
            if (callback != null) {
                callback.onResult(mSyteRemoteDataSource.handleOnFailure(e));
            }
            return;
        }
        mSyteRemoteDataSource.getBoundsWildAsync(context, imageSearch, callback);
    }

    public void getBoundsAsync(UrlImageSearch urlImageSearch, SyteCallback<BoundsResult> callback) {
        try {
            InputValidator.validateInput(urlImageSearch);
        } catch (SyteWrongInputException e) {
            if (callback != null) {
                callback.onResult(mSyteRemoteDataSource.handleOnFailure(e));
            }
            return;
        }
        mSyteRemoteDataSource.getBoundsAsync(urlImageSearch, callback);
    }

}

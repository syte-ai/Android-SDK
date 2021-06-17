package com.syte.ai.android_sdk.internal;

import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;

class ImageSearchClientImpl implements ImageSearchClient {

    private final SyteRemoteDataSource mSyteRemoteDataSource;
    private final AccountDataService mAccountDataService;

    ImageSearchClientImpl(SyteRemoteDataSource syteRemoteDataSource, AccountDataService accountDataService) {
        mSyteRemoteDataSource = syteRemoteDataSource;
        mAccountDataService = accountDataService;
    }

    @Override
    public SyteResult<BoundsResult> getBounds(ImageSearchRequestData imageSearchRequestData) {
        return null;
    }

    @Override
    public SyteResult<BoundsResult> getBounds(UrlImageSearchRequestData urlImageSearchRequestData) {
        return mSyteRemoteDataSource.getBounds(urlImageSearchRequestData, mAccountDataService);
    }

    @Override
    public SyteResult<OffersResult> getOffers(Bound bound) {
        return mSyteRemoteDataSource.getOffers(bound);
    }

    @Override
    public void getOffersAsync(Bound bound, SyteCallback<OffersResult> callback) {
        mSyteRemoteDataSource.getOffersAsync(bound, callback);
    }

    @Override
    public void getBoundsAsync(ImageSearchRequestData imageSearchRequestData, SyteCallback<BoundsResult> callback) {

    }

    @Override
    public void getBoundsAsync(UrlImageSearchRequestData urlImageSearchRequestData, SyteCallback<BoundsResult> callback) {
        mSyteRemoteDataSource.getBoundsAsync(urlImageSearchRequestData, mAccountDataService, callback);
    }

}

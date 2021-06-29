package com.syte.ai.android_sdk;

import android.content.Context;
import android.net.Uri;

import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.SyteConfiguration;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;
import com.syte.ai.android_sdk.enums.SyteProductType;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

public class UrlImageSearchManager implements SyteCallback<AccountDataService> {

    private UrlImageSearchFragment mUrlImageSearchFragment;
    private WildImageSearchFragment mWildImageSearchFragment;
    private BoundsFragment mBoundsFragment;
    private InitSyte mInitSyte;
    private boolean mSessionStarted = false;
    private BoundsResult mBoundsResult = null;
    private OffersResult mOffersResult = null;

    public void subscribe(UrlImageSearchFragment fragment) {
        mUrlImageSearchFragment = fragment;
        mBoundsResult = null;
        mOffersResult = null;
    }

    public void subscribe(WildImageSearchFragment fragment) {
        mWildImageSearchFragment = fragment;
        mBoundsResult = null;
        mOffersResult = null;
    }

    public void unsubscribe() {
        mUrlImageSearchFragment = null;
        mBoundsFragment = null;
        mWildImageSearchFragment = null;
    }

    public void subscribe(BoundsFragment fragment) {
        mBoundsFragment = fragment;
    }

    public void getOffersSync(Bound bound) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOffersResult = mInitSyte.retrieveImageSearchClient().getOffers(bound).data;
                if (mBoundsFragment != null) {
                    mBoundsFragment.onOffersRetrieved(mOffersResult);
                }
            }
        }).start();
    }

    public OffersResult getLastRetrievedOffers() {
        return mOffersResult;
    }

    public void getOffersAsync(Bound bound) {
        mInitSyte.retrieveImageSearchClient().getOffersAsync(bound, new SyteCallback<OffersResult>() {
            @Override
            public void onResult(SyteResult<OffersResult> syteResult) {
                mOffersResult = syteResult.data;
                if (mBoundsFragment != null) {
                    mBoundsFragment.onOffersRetrieved(mOffersResult);
                }
            }
        });
    }

    public void getBoundsSync(
            String url,
            String sku,
            boolean retrieveOffersForTheFirstBound
    ) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UrlImageSearchRequestData urlImageSearchRequestData = new UrlImageSearchRequestData(
                        url,
                        SyteProductType.DISCOVERY_BUTTON
                );
                urlImageSearchRequestData.setSku(sku);
                urlImageSearchRequestData.setRetrieveOffersForTheFirstBound(retrieveOffersForTheFirstBound);
                SyteResult<BoundsResult> result = mInitSyte.retrieveImageSearchClient().getBounds(
                        urlImageSearchRequestData
                );

                mBoundsResult = result.data;
                if (mUrlImageSearchFragment != null) {
                    mUrlImageSearchFragment.showToast("Successful - " + result.isSuccessful);
                    mUrlImageSearchFragment.onBoundsRetrieved(result.data);
                }
            }
        }).start();
    }

    public void getBoundsSyncWild(
            Uri imageUri,
            boolean retrieveOffersForTheFirstBound
    ) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageSearchRequestData imageSearchRequestData = new ImageSearchRequestData(imageUri);
                imageSearchRequestData.setRetrieveOffersForTheFirstBound(retrieveOffersForTheFirstBound);

                Context context = mUrlImageSearchFragment == null ?
                        mWildImageSearchFragment.requireActivity().getApplicationContext() :
                        mUrlImageSearchFragment.requireActivity().getApplicationContext();

                SyteResult<BoundsResult> result = mInitSyte.retrieveImageSearchClient().getBounds(
                        context,
                        imageSearchRequestData
                );

                mBoundsResult = result.data;
                if (mWildImageSearchFragment != null) {
                    mWildImageSearchFragment.showToast("Successful - " + result.isSuccessful);
                    mWildImageSearchFragment.onBoundsRetrieved(result.data);
                }
            }
        }).start();
    }

    public BoundsResult getBoundsResult() {
        return mBoundsResult;
    }

    public void getBoundsAsync(String url, String sku, boolean retrieveOffersForTheFirstBound) {
        UrlImageSearchRequestData urlImageSearchRequestData = new UrlImageSearchRequestData(
                url,
                SyteProductType.DISCOVERY_BUTTON
        );
        urlImageSearchRequestData.setSku(sku);
        urlImageSearchRequestData.setRetrieveOffersForTheFirstBound(retrieveOffersForTheFirstBound);
        mInitSyte.retrieveImageSearchClient().getBoundsAsync(urlImageSearchRequestData, new SyteCallback<BoundsResult>() {
            @Override
            public void onResult(SyteResult<BoundsResult> syteResult) {
                mBoundsResult = syteResult.data;
                if (mUrlImageSearchFragment != null) {
                    mUrlImageSearchFragment.showToast("Successful - " + syteResult.isSuccessful);
                    mUrlImageSearchFragment.onBoundsRetrieved(syteResult.data);
                }
            }
        });
    }

    public void getBoundsWildAsync(Uri imageUri, boolean retrieveOffersForTheFirstBound) {
        ImageSearchRequestData imageSearchRequestData = new ImageSearchRequestData(imageUri);
        imageSearchRequestData.setRetrieveOffersForTheFirstBound(retrieveOffersForTheFirstBound);

        Context context = mUrlImageSearchFragment == null ?
                mWildImageSearchFragment.requireActivity().getApplicationContext() :
                mUrlImageSearchFragment.requireActivity().getApplicationContext();

        mInitSyte.retrieveImageSearchClient().getBoundsAsync(
                context, imageSearchRequestData, new SyteCallback<BoundsResult>() {
            @Override
            public void onResult(SyteResult<BoundsResult> syteResult) {
                mBoundsResult = syteResult.data;
                if (mWildImageSearchFragment != null) {
                    mWildImageSearchFragment.showToast("Successful - " + syteResult.isSuccessful);
                    mWildImageSearchFragment.onBoundsRetrieved(syteResult.data);
                }
            }
        });
    }

    public void initSyte() throws SyteInitializationException {
        if (mUrlImageSearchFragment == null && mWildImageSearchFragment == null) {
            return;
        }
        mInitSyte = InitSyte.getInstance();
        Context context = mUrlImageSearchFragment == null ?
                mWildImageSearchFragment.requireActivity().getApplicationContext() :
                mUrlImageSearchFragment.requireActivity().getApplicationContext();
        SyteConfiguration syteConfiguration = new SyteConfiguration(
                context,
                "9186",
                "602e43d2d6ddcd558359f91f"
        );
        mInitSyte.startSessionAsync(syteConfiguration, this);
    }

    @Override
    public void onResult(SyteResult<AccountDataService> syteResult) {
        // We can work with the Syte SDK now
        if (syteResult.isSuccessful) {
            mSessionStarted = true;
        } else {
            if (mUrlImageSearchFragment != null) {
                mUrlImageSearchFragment.showToast(null);
            } else if (mWildImageSearchFragment != null) {
                mWildImageSearchFragment.showToast(null);
            }
        }
    }

}

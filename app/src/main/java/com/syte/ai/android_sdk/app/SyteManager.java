package com.syte.ai.android_sdk.app;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.core.InitSyte;
import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;
import com.syte.ai.android_sdk.enums.SyteProductType;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

public class SyteManager implements SyteCallback<SytePlatformSettings> {

    private UrlImageSearchFragment mUrlImageSearchFragment;
    private WildImageSearchFragment mWildImageSearchFragment;
    private BoundsFragment mBoundsFragment;
    private InitSyte mInitSyte;
    private boolean mSessionStarted = false;
    private BoundsResult mBoundsResult = null;
    private OffersResult mOffersResult = null;
    private boolean mZipOffers = false;
    public Activity mActivity;

    private SimilarProductsResult mSimilarProductsResult;
    private ShopTheLookResult mShopTheLookResult;
    private PersonalizationResult mPersonalizationResult;
    private CropCoordinates mCropCoordinates;

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

    public void zipOffers(boolean zip) {
        mZipOffers = zip;
    }

    public boolean zipOffers() {
        return mZipOffers;
    }

    public void getOffersSync(Bound bound) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mOffersResult = mInitSyte.getImageSearchClient().getOffers(
                        bound,
                        null
                ).data;
                if (mBoundsFragment != null) {
                    mBoundsFragment.onOffersRetrieved(mOffersResult);
                }
            }
        }).start();
    }

    public OffersResult getLastRetrievedOffers() {
        return mOffersResult;
    }

    public void setSimilarProductsResult(SimilarProductsResult similarProductsResult) {
        mSimilarProductsResult = similarProductsResult;
    }

    public SimilarProductsResult getSimilarProductsResult() {
        return mSimilarProductsResult;
    }

    public void getOffersAsync(Bound bound) {
        mInitSyte.getImageSearchClient().getOffersAsync(
                bound,
                mCropCoordinates,
                new SyteCallback<OffersResult>() {
            @Override
            public void onResult(SyteResult<OffersResult> syteResult) {
                if (syteResult.errorMessage != null) {
                    Toast.makeText(SDKApplication.getInstance(), syteResult.errorMessage, Toast.LENGTH_LONG).show();
                }
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
                if (!SDKApplication.getInstance().getSessionSKUs().isEmpty()) {
                    for (String skuPdp : SDKApplication.getInstance().getSessionSKUs()) {
                        mInitSyte.addViewedProduct(skuPdp);
                    }
                    urlImageSearchRequestData.setPersonalizedRanking(true);
                }
                urlImageSearchRequestData.setSku(sku);
                urlImageSearchRequestData.setRetrieveOffersForTheFirstBound(retrieveOffersForTheFirstBound);
                SyteResult<BoundsResult> result = mInitSyte.getImageSearchClient().getBounds(
                        urlImageSearchRequestData
                );

                if (result.errorMessage != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SDKApplication.getInstance(), result.errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }

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
                if (!SDKApplication.getInstance().getSessionSKUs().isEmpty()) {
                    for (String skuPdp : SDKApplication.getInstance().getSessionSKUs()) {
                        mInitSyte.addViewedProduct(skuPdp);
                    }
                    imageSearchRequestData.setPersonalizedRanking(true);
                }
                Context context = mUrlImageSearchFragment == null ?
                        mWildImageSearchFragment.requireActivity().getApplicationContext() :
                        mUrlImageSearchFragment.requireActivity().getApplicationContext();

                SyteResult<BoundsResult> result = mInitSyte.getImageSearchClient().getBounds(
                        context,
                        imageSearchRequestData
                );
                if (result.errorMessage != null) {
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SDKApplication.getInstance(), result.errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
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
        if (!SDKApplication.getInstance().getSessionSKUs().isEmpty()) {
            for (String skuPdp : SDKApplication.getInstance().getSessionSKUs()) {
                mInitSyte.addViewedProduct(skuPdp);
            }
            urlImageSearchRequestData.setPersonalizedRanking(true);
        }
        urlImageSearchRequestData.setSku(sku);
        urlImageSearchRequestData.setRetrieveOffersForTheFirstBound(retrieveOffersForTheFirstBound);
        mInitSyte.getImageSearchClient().getBoundsAsync(urlImageSearchRequestData, new SyteCallback<BoundsResult>() {
            @Override
            public void onResult(SyteResult<BoundsResult> syteResult) {
                if (syteResult.errorMessage != null) {
                    Toast.makeText(SDKApplication.getInstance(), syteResult.errorMessage, Toast.LENGTH_LONG).show();
                }
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
        if (!SDKApplication.getInstance().getSessionSKUs().isEmpty()) {
            for (String sku : SDKApplication.getInstance().getSessionSKUs()) {
                mInitSyte.addViewedProduct(sku);
            }
            imageSearchRequestData.setPersonalizedRanking(true);
        }

        Context context = mUrlImageSearchFragment == null ?
                mWildImageSearchFragment.requireActivity().getApplicationContext() :
                mUrlImageSearchFragment.requireActivity().getApplicationContext();

        mInitSyte.getImageSearchClient().getBoundsAsync(
                context, imageSearchRequestData, new SyteCallback<BoundsResult>() {
            @Override
            public void onResult(SyteResult<BoundsResult> syteResult) {
                if (syteResult.errorMessage != null) {
                    Toast.makeText(SDKApplication.getInstance(), syteResult.errorMessage, Toast.LENGTH_LONG).show();
                }
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
        mInitSyte = InitSyte.newInstance();
        Context context = mUrlImageSearchFragment == null ?
                mWildImageSearchFragment.requireActivity().getApplicationContext() :
                mUrlImageSearchFragment.requireActivity().getApplicationContext();
        SyteConfiguration syteConfiguration = new SyteConfiguration(
                context,
                "9165",
                "601c206d0a7f780efb9360f3"
        );
        mInitSyte.startSessionAsync(syteConfiguration, this);
    }

    @Override
    public void onResult(SyteResult<SytePlatformSettings> syteResult) {
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

    public void setCoordinates(CropCoordinates coordinates) {
        mCropCoordinates = coordinates;
    }

    public void setShopTheLookResult(ShopTheLookResult data) {
        mShopTheLookResult = data;
    }

    public ShopTheLookResult getShopTheLookResult() {
        return mShopTheLookResult;
    }

    public void setPersonalizationResult(PersonalizationResult data) {
        mPersonalizationResult = data;
    }

    public PersonalizationResult getPersonalizationResult() {
        return mPersonalizationResult;
    }

}

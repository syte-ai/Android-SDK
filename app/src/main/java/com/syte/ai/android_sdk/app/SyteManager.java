package com.syte.ai.android_sdk.app;

import android.content.Context;
import android.widget.Toast;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.core.InitSyte;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.data.ShopTheLookRequestData;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.Offer;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SyteManager {

    private final InitSyte mInitSyte = InitSyte.newInstance();
    private final Context mContext;
    private List<Offer> mLastRetrievedItemsList;
    private List<Bound> mLastRetrievedBoundsList;

    public SyteManager(Context context) {
        mContext = context;
    }

    private void showToastError(String error) {
        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
    }

    public void setLocale(String locale) {
        SyteConfiguration configuration = mInitSyte.getConfiguration();
        configuration.setLocale(locale);
        try {
            mInitSyte.setConfiguration(configuration);
        } catch (SyteWrongInputException e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getLocale() {
        return mInitSyte.getConfiguration().getLocale();
    }

    public void initialize(SyteCallback<Boolean> callback) {
        SyteConfiguration syteConfiguration = new SyteConfiguration(
                mContext,
                mContext.getResources().getString(R.string.default_account_id),
                mContext.getResources().getString(R.string.default_sig)
        );
        syteConfiguration.setLocale("en_US");
        mInitSyte.startSessionAsync(syteConfiguration, new SyteCallback<Boolean>() {
            @Override
            public void onResult(SyteResult<Boolean> syteResult) {
                if (!syteResult.isSuccessful) {
                    showToastError(syteResult.errorMessage);
                }
                callback.onResult(syteResult);
            }
        });
    }

    public void uninitialize() {
        mInitSyte.endSession();
    }

    public Set<String> getViewedProducts() {
        return mInitSyte.getViewedProducts();
    }

    public void addViewedProducts(Set<String> viewedProducts) {
        for (String viewedProduct : viewedProducts) {
            try {
                mInitSyte.addViewedProduct(viewedProduct);
            } catch (SyteWrongInputException e) {}
        }
    }

    public void setLastRetrievedItemsList(List<Offer> itemsList) {
        mLastRetrievedItemsList = itemsList;
    }

    public List<Offer> getLastRetrievedItemsList() {
        return mLastRetrievedItemsList == null ? new ArrayList<>() : mLastRetrievedItemsList;
    }

    public void setLastRetrievedBoundsList(List<Bound> boundsList) {
        mLastRetrievedBoundsList = boundsList;
    }

    public List<Bound> getLastRetrievedBoundsList() {
        return mLastRetrievedBoundsList;
    }

    public void personalization(
            PersonalizationRequestData personalizationRequestData,
            SyteCallback<PersonalizationResult> callback
    ) {
        mInitSyte.getProductRecommendationClient().getPersonalizationAsync(
                personalizationRequestData,
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void shopTheLook(
            ShopTheLookRequestData shopTheLookRequestData,
            SyteCallback<ShopTheLookResult> callback
    ) {
        mInitSyte.getProductRecommendationClient().getShopTheLookAsync(
                shopTheLookRequestData,
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void getSimilars(
            SimilarProductsRequestData similarProductsRequestData,
            SyteCallback<SimilarProductsResult> callback
    ) {
        mInitSyte.getProductRecommendationClient().getSimilarProductsAsync(
                similarProductsRequestData,
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void urlImageSearch(
            UrlImageSearchRequestData requestData,
            SyteCallback<BoundsResult> callback
    ) {
        mInitSyte.getImageSearchClient().getBoundsAsync(
                requestData,
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void getOffers(
            Bound bound,
            SyteCallback<OffersResult> callback
    ) {
        mInitSyte.getImageSearchClient().getOffersAsync(
                bound,
                null,
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void wildImageSearch(
            ImageSearchRequestData requestData,
            SyteCallback<BoundsResult> callback
    ) {
        mInitSyte.getImageSearchClient().getBoundsAsync(
                mContext,
                requestData,
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void clearCachedItems() {
        mLastRetrievedItemsList = null;
    }

    public void clearCachedBounds() {
        mLastRetrievedBoundsList = null;
    }

}

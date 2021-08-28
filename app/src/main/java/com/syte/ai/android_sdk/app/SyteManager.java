package com.syte.ai.android_sdk.app;

import android.content.Context;
import android.widget.Toast;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.core.InitSyte;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.ImageSearch;
import com.syte.ai.android_sdk.data.Personalization;
import com.syte.ai.android_sdk.data.ShopTheLook;
import com.syte.ai.android_sdk.data.SimilarProducts;
import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.UrlImageSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.auto_complete.AutoCompleteResult;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.Item;
import com.syte.ai.android_sdk.data.result.offers.ItemsResult;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SyteManager {

    private final InitSyte mInitSyte = InitSyte.newInstance();
    private final Context mContext;
    private List<Item> mLastRetrievedItemsList;
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
                mInitSyte.addViewedItem(viewedProduct);
            } catch (SyteWrongInputException e) {}
        }
    }

    public void setLastRetrievedItemsList(List<Item> itemsList) {
        mLastRetrievedItemsList = itemsList;
    }

    public List<Item> getLastRetrievedItemsList() {
        return mLastRetrievedItemsList == null ? new ArrayList<>() : mLastRetrievedItemsList;
    }

    public void setLastRetrievedBoundsList(List<Bound> boundsList) {
        mLastRetrievedBoundsList = boundsList;
    }

    public List<Bound> getLastRetrievedBoundsList() {
        return mLastRetrievedBoundsList;
    }

    public void personalization(
            Personalization personalization,
            SyteCallback<PersonalizationResult> callback
    ) {
        mInitSyte.getProductRecommendationClient().getPersonalizedProductsAsync(
                personalization,
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void getAutoComplete(String query, SyteCallback<AutoCompleteResult> callback) {
        mInitSyte.getTextSearchClient().getAutoCompleteAsync(
                query,
                mInitSyte.getConfiguration().getLocale(),
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void getPopularSearch(SyteCallback<List<String>> callback) {
        mInitSyte.getTextSearchClient().getPopularSearchAsync(
                mInitSyte.getConfiguration().getLocale(),
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                });
    }

    public void getTextSearch(String query, SyteCallback<TextSearchResult> callback) {
        TextSearch textSearch = new TextSearch(query, mInitSyte.getConfiguration().getLocale());

        mInitSyte.getTextSearchClient().getTextSearchAsync(textSearch, syteResult -> {
            if (!syteResult.isSuccessful) {
                showToastError(syteResult.errorMessage);
            }
            callback.onResult(syteResult);
        });
    }

    public void shopTheLook(
            ShopTheLook shopTheLook,
            SyteCallback<ShopTheLookResult> callback
    ) {
        mInitSyte.getProductRecommendationClient().getShopTheLookAsync(
                shopTheLook,
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void getSimilars(
            SimilarProducts similarProducts,
            SyteCallback<SimilarProductsResult> callback
    ) {
        mInitSyte.getProductRecommendationClient().getSimilarProductsAsync(
                similarProducts,
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void urlImageSearch(
            UrlImageSearch requestData,
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
            SyteCallback<ItemsResult> callback
    ) {
        mInitSyte.getImageSearchClient().getItemsForBoundAsync(
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
            ImageSearch requestData,
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

    public List<String> getSearchHistory() {
        return mInitSyte.getRecentTextSearches();
    }
}

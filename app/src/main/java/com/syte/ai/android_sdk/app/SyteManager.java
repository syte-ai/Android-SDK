package com.syte.ai.android_sdk.app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.core.Syte;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.ImageSearch;
import com.syte.ai.android_sdk.data.Personalization;
import com.syte.ai.android_sdk.data.ShopTheLook;
import com.syte.ai.android_sdk.data.SimilarItems;
import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.UrlImageSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class SyteManager {

    private Syte mSyte;
    private final Context mContext;
    private List<Item> mLastRetrievedItemsList;
    private List<Bound> mLastRetrievedBoundsList;

    public SyteManager(Context context) {
        mContext = context;
        initialize();
    }

    private void showToastError(String error) {
        Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
    }

    public void setLocale(String locale) {
        SyteConfiguration configuration = mSyte.getConfiguration();
        configuration.setLocale(locale);
        try {
            mSyte.setConfiguration(configuration);
        } catch (SyteWrongInputException e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public String getLocale() {
        return mSyte.getConfiguration().getLocale();
    }

    public void initialize() {
        SyteConfiguration syteConfiguration = new SyteConfiguration(
                mContext,
                mContext.getResources().getString(R.string.default_account_id),
                mContext.getResources().getString(R.string.default_sig)
        );
        syteConfiguration.setLocale("en_US");
//        syteConfiguration.enableLocalStorage(false);
        mSyte = Syte.newInstance(syteConfiguration);
    }

    public Set<String> getViewedProducts() {
        return mSyte.getViewedProducts();
    }

    public void addViewedProducts(Set<String> viewedProducts) {
        for (String viewedProduct : viewedProducts) {
            try {
                mSyte.addViewedItem(viewedProduct);
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
        mSyte.getPersonalizedProductsAsync(
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
        mSyte.getAutoComplete(
                query,
                mSyte.getConfiguration().getLocale(),
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                }
        );
    }

    public void getPopularSearch(SyteCallback<List<String>> callback) {
        mSyte.getPopularSearchAsync(
                mSyte.getConfiguration().getLocale(),
                syteResult -> {
                    if (!syteResult.isSuccessful) {
                        showToastError(syteResult.errorMessage);
                    }
                    callback.onResult(syteResult);
                });
    }

    public void getTextSearch(String query, SyteCallback<TextSearchResult> callback) {
        TextSearch textSearch = new TextSearch(query, mSyte.getConfiguration().getLocale());

        mSyte.getTextSearchAsync(textSearch, syteResult -> {
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
        mSyte.getShopTheLookAsync(
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
            SimilarItems similarProducts,
            SyteCallback<SimilarProductsResult> callback
    ) {
        mSyte.getSimilarProductsAsync(
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
        mSyte.getBoundsAsync(
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
        mSyte.getItemsForBoundAsync(
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
        mSyte.getBoundsAsync(
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
        return mSyte.getRecentTextSearches();
    }
}

package com.syte.ai.android_sdk.core;

import android.content.Context;

import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.ImageSearch;
import com.syte.ai.android_sdk.data.Personalization;
import com.syte.ai.android_sdk.data.ShopTheLook;
import com.syte.ai.android_sdk.data.SimilarItems;
import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.UrlImageSearch;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.auto_complete.AutoCompleteResult;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.ItemsResult;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.events.EventInitialization;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;
import com.syte.ai.android_sdk.util.SyteLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class SyteImpl extends Syte {

    private static final String TAG = "Syte";

    private enum SyteState {
        IDLE, INITIALIZED
    }

    private SyteConfiguration mConfiguration;
    private SyteRemoteDataSource mRemoteDataSource;
    private SytePlatformSettings mSytePlatformSettings;
    private EventsRemoteDataSource mEventsRemoteDataSource;
    private SyteState mState = SyteState.IDLE;
    private TextSearchClientImpl mTextSearchClient = null;
    private ImageSearchClientImpl mImageSearchClient = null;
    private ProductRecommendationClientImpl mProductRecommendationClient = null;

    SyteImpl(SyteConfiguration configuration) {
        mConfiguration = configuration;
        mRemoteDataSource = new SyteRemoteDataSource(mConfiguration);
        mEventsRemoteDataSource = new EventsRemoteDataSource(mConfiguration);
    }

    void startSessionAsync(SyteCallback<Boolean> callback) {
        mRemoteDataSource.initializeAsync(syteResult -> {
            if (syteResult.isSuccessful) {
                mSytePlatformSettings = syteResult.data;
                mTextSearchClient = new TextSearchClientImpl(
                        mRemoteDataSource,
                        mConfiguration.getAllowAutoCompletionQueue()
                );
                mProductRecommendationClient = new ProductRecommendationClientImpl(
                        mRemoteDataSource, mSytePlatformSettings
                );
                mImageSearchClient = new ImageSearchClientImpl(
                        mRemoteDataSource,
                        mSytePlatformSettings
                );
                mState = SyteState.INITIALIZED;
            } else {
                mState = SyteState.IDLE;
            }
            if (mState == SyteState.INITIALIZED) {
                fireEvent(new EventInitialization());
            }
            if (callback != null) {
                SyteResult<Boolean> result = new SyteResult<>();
                result.data = syteResult.isSuccessful;
                result.resultCode = syteResult.resultCode;
                result.isSuccessful = syteResult.isSuccessful;
                result.errorMessage = syteResult.errorMessage;
                callback.onResult(result);
            }
        });
    }

    @Override
    public SyteConfiguration getConfiguration() {
        return mConfiguration;
    }

    @Override
    public void setConfiguration(SyteConfiguration configuration) throws SyteWrongInputException {
        InputValidator.validateInput(configuration);
        verifyInitialized();
        mConfiguration = configuration;
        mRemoteDataSource.setConfiguration(configuration);
        mEventsRemoteDataSource.setConfiguration(configuration);
        mTextSearchClient.setAllowAutoCompletionQueue(configuration.getAllowAutoCompletionQueue());
    }

    @Override
    public SytePlatformSettings getSytePlatformSettings() {
        verifyInitialized();
        return mSytePlatformSettings;
    }

    @Override
    public void fireEvent(BaseSyteEvent event) {
        verifyInitialized();
        mEventsRemoteDataSource.fireEvent(event);
    }

    @Override
    public void addViewedItem(String sku) throws SyteWrongInputException {
        verifyInitialized();
        InputValidator.validateInput(sku);
        mConfiguration.addViewedProduct(sku);
    }

    @Override
    public Set<String> getViewedProducts() {
        if (mConfiguration != null) {
            return mConfiguration.getViewedProducts();
        } else {
            return new HashSet<>();
        }
    }

    @Override
    public List<String> getRecentTextSearches() {
        if (mConfiguration != null) {
            String searchTerms = mConfiguration.getStorage().getTextSearchTerms();
            if (!searchTerms.isEmpty()) {
                return Arrays.asList(searchTerms.split(","));
            }
        }

        return new ArrayList<>();
    }

    @Override
    public void setLogLevel(SyteLogger.LogLevel logLevel) {
        SyteLogger.setLogLevel(logLevel);
    }

    @Override
    public SyteResult<BoundsResult> getBounds(Context context, ImageSearch imageSearch) {
        return mImageSearchClient.getBounds(context, imageSearch);
    }

    @Override
    public SyteResult<BoundsResult> getBounds(UrlImageSearch urlImageSearch) {
        return mImageSearchClient.getBounds(urlImageSearch);
    }

    @Override
    public SyteResult<ItemsResult> getItemsForBound(Bound bound, CropCoordinates cropCoordinates) {
        return mImageSearchClient.getItemsForBound(bound, cropCoordinates);
    }

    @Override
    public void getItemsForBoundAsync(Bound bound, CropCoordinates cropCoordinates, SyteCallback<ItemsResult> callback) {
        mImageSearchClient.getItemsForBoundAsync(bound, cropCoordinates, callback);
    }

    @Override
    public void getBoundsAsync(Context context, ImageSearch imageSearch, SyteCallback<BoundsResult> callback) {
        mImageSearchClient.getBoundsAsync(context, imageSearch, callback);
    }

    @Override
    public void getBoundsAsync(UrlImageSearch urlImageSearch, SyteCallback<BoundsResult> callback) {
        mImageSearchClient.getBoundsAsync(urlImageSearch, callback);
    }

    @Override
    public SyteResult<SimilarProductsResult> getSimilarProducts(SimilarItems similarProducts) {
        return mProductRecommendationClient.getSimilarProducts(similarProducts);
    }

    @Override
    public void getSimilarProductsAsync(SimilarItems similarProducts, SyteCallback<SimilarProductsResult> callback) {
        mProductRecommendationClient.getSimilarProductsAsync(similarProducts, callback);
    }

    @Override
    public SyteResult<ShopTheLookResult> getShopTheLook(ShopTheLook shopTheLook) {
        return mProductRecommendationClient.getShopTheLook(shopTheLook);
    }

    @Override
    public void getShopTheLookAsync(ShopTheLook shopTheLook, SyteCallback<ShopTheLookResult> callback) {
        mProductRecommendationClient.getShopTheLookAsync(shopTheLook, callback);
    }

    @Override
    public SyteResult<PersonalizationResult> getPersonalizedProducts(Personalization personalization) {
        return mProductRecommendationClient.getPersonalizedProducts(personalization);
    }

    @Override
    public void getPersonalizedProductsAsync(Personalization personalization, SyteCallback<PersonalizationResult> callback) {
        mProductRecommendationClient.getPersonalizedProductsAsync(personalization, callback);
    }

    @Override
    public SyteResult<List<String>> getPopularSearch(String lang) {
        return mTextSearchClient.getPopularSearch(lang);
    }

    @Override
    public void getPopularSearchAsync(String lang, SyteCallback<List<String>> callback) {
        mTextSearchClient.getPopularSearchAsync(lang, callback);
    }

    @Override
    public SyteResult<TextSearchResult> getTextSearch(TextSearch textSearch) {
        return mTextSearchClient.getTextSearch(textSearch);
    }

    @Override
    public void getTextSearchAsync(TextSearch textSearch, SyteCallback<TextSearchResult> callback) {
        mTextSearchClient.getTextSearchAsync(textSearch, callback);
    }

    @Override
    public void getAutoComplete(String query, String lang, SyteCallback<AutoCompleteResult> callback) {
        mTextSearchClient.getAutoCompleteAsync(query, lang, callback);
    }

    private void verifyInitialized() throws SyteInitializationException {
        if (mState != SyteState.INITIALIZED) {
            throw new SyteInitializationException("Syte is not initialized.");
        }
    }

}

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
import com.syte.ai.android_sdk.events.EventPageView;
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

    private SyteConfiguration mConfiguration;
    private final SyteRemoteDataSource mRemoteDataSource;
    private final EventsRemoteDataSource mEventsRemoteDataSource;
    private final TextSearchClientImpl mTextSearchClient;
    private final ImageSearchClientImpl mImageSearchClient;
    private ProductRecommendationClientImpl mProductRecommendationClient = null;

    SyteImpl(SyteConfiguration configuration) {
        mConfiguration = configuration;
        mRemoteDataSource = new SyteRemoteDataSource(mConfiguration);
        mEventsRemoteDataSource = new EventsRemoteDataSource(mConfiguration);
        mTextSearchClient = new TextSearchClientImpl(
                mRemoteDataSource,
                mConfiguration.getAllowAutoCompletionQueue()
        );
        mProductRecommendationClient = new ProductRecommendationClientImpl(
                mRemoteDataSource
        );
        mImageSearchClient = new ImageSearchClientImpl(
                mRemoteDataSource
        );
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
    public SyteResult<SytePlatformSettings> getPlatformSettings() {
        verifyInitialized();
        return mRemoteDataSource.initialize();
    }

    @Override
    public void getPlatformSettingsAsync(SyteCallback<SytePlatformSettings> syteCallback) {
        verifyInitialized();
        mRemoteDataSource.initializeAsync(syteResult -> {
            if (syteCallback != null) {
                syteCallback.onResult(syteResult);
            }
        });
    }

    @Override
    public void fireEvent(BaseSyteEvent event) {
        verifyInitialized();
        mEventsRemoteDataSource.fireEvent(event);
        if (event instanceof EventPageView) {
            try {
                addViewedProduct(((EventPageView) event).getSku());
            } catch (SyteWrongInputException e) {}
        }
    }

    @Override
    public void addViewedProduct(String sku) throws SyteWrongInputException {
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
    public SyteResult<BoundsResult> getBoundsForImage(Context context, ImageSearch imageSearch) {
        verifyInitialized();
        return mImageSearchClient.getBounds(context, imageSearch);
    }

    @Override
    public SyteResult<BoundsResult> getBoundsForImageUrl(UrlImageSearch urlImageSearch) {
        verifyInitialized();
        return mImageSearchClient.getBounds(urlImageSearch);
    }

    @Override
    public SyteResult<ItemsResult> getItemsForBound(Bound bound, CropCoordinates cropCoordinates) {
        verifyInitialized();
        return mImageSearchClient.getItemsForBound(bound, cropCoordinates);
    }

    @Override
    public void getItemsForBoundAsync(Bound bound, CropCoordinates cropCoordinates, SyteCallback<ItemsResult> callback) {
        verifyInitialized();
        mImageSearchClient.getItemsForBoundAsync(bound, cropCoordinates, callback);
    }

    @Override
    public void getBoundsForImageAsync(Context context, ImageSearch imageSearch, SyteCallback<BoundsResult> callback) {
        verifyInitialized();
        mImageSearchClient.getBoundsAsync(context, imageSearch, callback);
    }

    @Override
    public void getBoundsForImageUrlAsync(UrlImageSearch urlImageSearch, SyteCallback<BoundsResult> callback) {
        verifyInitialized();
        mImageSearchClient.getBoundsAsync(urlImageSearch, callback);
    }

    @Override
    public SyteResult<SimilarProductsResult> getSimilarProducts(SimilarItems similarProducts) {
        verifyInitialized();
        return mProductRecommendationClient.getSimilarProducts(similarProducts);
    }

    @Override
    public void getSimilarProductsAsync(SimilarItems similarProducts, SyteCallback<SimilarProductsResult> callback) {
        verifyInitialized();
        mProductRecommendationClient.getSimilarProductsAsync(similarProducts, callback);
    }

    @Override
    public SyteResult<ShopTheLookResult> getShopTheLook(ShopTheLook shopTheLook) {
        verifyInitialized();
        return mProductRecommendationClient.getShopTheLook(shopTheLook);
    }

    @Override
    public void getShopTheLookAsync(ShopTheLook shopTheLook, SyteCallback<ShopTheLookResult> callback) {
        verifyInitialized();
        mProductRecommendationClient.getShopTheLookAsync(shopTheLook, callback);
    }

    @Override
    public SyteResult<PersonalizationResult> getPersonalizedProducts(Personalization personalization) {
        verifyInitialized();
        return mProductRecommendationClient.getPersonalizedProducts(personalization);
    }

    @Override
    public void getPersonalizedProductsAsync(Personalization personalization, SyteCallback<PersonalizationResult> callback) {
        verifyInitialized();
        mProductRecommendationClient.getPersonalizedProductsAsync(personalization, callback);
    }

    @Override
    public SyteResult<List<String>> getPopularSearches(String lang) {
        verifyInitialized();
        return mTextSearchClient.getPopularSearch(lang);
    }

    @Override
    public void getPopularSearchesAsync(String lang, SyteCallback<List<String>> callback) {
        verifyInitialized();
        mTextSearchClient.getPopularSearchAsync(lang, callback);
    }

    @Override
    public SyteResult<TextSearchResult> getTextSearch(TextSearch textSearch) {
        verifyInitialized();
        return mTextSearchClient.getTextSearch(textSearch);
    }

    @Override
    public void getTextSearchAsync(TextSearch textSearch, SyteCallback<TextSearchResult> callback) {
        verifyInitialized();
        mTextSearchClient.getTextSearchAsync(textSearch, callback);
    }

    @Override
    public void getAutoCompleteForTextSearch(String query, String lang, SyteCallback<AutoCompleteResult> callback) {
        verifyInitialized();
        mTextSearchClient.getAutoCompleteAsync(query, lang, callback);
    }

    @Override
    public void getAutoCompleteForTextSearch(String query, SyteCallback<AutoCompleteResult> callback) {
        verifyInitialized();
        mTextSearchClient.getAutoCompleteAsync(query, mConfiguration.getLocale(), callback);
    }

    private void verifyInitialized() throws SyteInitializationException {
//        if (mState != SyteState.INITIALIZED) {
//            throw new SyteInitializationException("Syte is not initialized.");
//        }
    }

}

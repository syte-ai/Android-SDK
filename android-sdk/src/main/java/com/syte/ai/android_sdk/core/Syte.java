package com.syte.ai.android_sdk.core;

import android.content.Context;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.ImageSearch;
import com.syte.ai.android_sdk.data.Personalization;
import com.syte.ai.android_sdk.data.ShopTheLook;
import com.syte.ai.android_sdk.data.SimilarItems;
import com.syte.ai.android_sdk.data.TextSearch;
import com.syte.ai.android_sdk.data.UrlImageSearch;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
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
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;
import com.syte.ai.android_sdk.util.SyteLogger;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;


public abstract class Syte {

    private static final String TAG = "Syte";

    /**
     * Creates a new instance of the Syte class.
     *
     * @param configuration {@link SyteConfiguration}
     * @return new instance
     */
    public static Syte newInstance(SyteConfiguration configuration) {
        try {
            InputValidator.validateInput(configuration);
        } catch (SyteWrongInputException e) {
            throw new SyteInitializationException(e.getMessage());
        }
        return new SyteImpl(configuration);
    }

    /**
     * Retrieve current configuration.
     * @return {@link SyteConfiguration}
     */
    public abstract SyteConfiguration getConfiguration();

    /**
     * Apply new configuration
     * @param configuration {@link SyteConfiguration}
     * @throws SyteWrongInputException
     */
    public abstract void setConfiguration(SyteConfiguration configuration) throws SyteWrongInputException;

    /**
     * Retrieve {@link SytePlatformSettings}.
     * This method must not be called on the UI thread!
     * @return {@link SytePlatformSettings}
     */
    public abstract SyteResult<SytePlatformSettings> getSytePlatformSettings();

    /**
     * Retrieve {@link SytePlatformSettings}.
     * Asynchronous equivalent of {@link Syte#getSytePlatformSettings()}
     * @param syteCallback {@link SyteCallback} result callback
     */
    public abstract void getSytePlatformSettingsAsync(SyteCallback<SytePlatformSettings> syteCallback);

    /**
     * Send event to Syte. Can be used to send either predefined events {@link com.syte.ai.android_sdk.events} or a custom event.
     * Extend {@link BaseSyteEvent} to send custom events.
     * @param event event to send. Must extend {@link BaseSyteEvent}
     */
    public abstract void fireEvent(BaseSyteEvent event);

    /**
     * Save product ID into the local storage. All saved viewed products will be used for personalization.
     * @param sku product ID
     */
    public abstract void addViewedItem(String sku) throws SyteWrongInputException;

    /**
     * Get all product IDs that were viewed during this session.
     * @return list of product IDs.
     */
    public abstract Set<String> getViewedProducts();

    /**
     * Retrieves the list of recent text searches.
     * NOTE: searches with no results will not be saved.
     * @return list of recent text searches.
     */
    public abstract List<String> getRecentTextSearches();

    /**
     * Set log level.
     * @param logLevel log level to be set
     */
    public abstract void setLogLevel(SyteLogger.LogLevel logLevel);


    /**
     * Retrieves bounds
     * Synchronous method. Must NOT be called on the UI thread!
     * @param context context
     * @param imageSearch {@link ImageSearch}
     * @return {@link SyteResult}
     */
    public abstract SyteResult<BoundsResult> getBounds(Context context, ImageSearch imageSearch);

    /**
     * Retrieves bounds
     * Synchronous method. Must NOT be called on the UI thread!
     * @param urlImageSearch {@link UrlImageSearch}
     * @return {@link SyteResult}
     */
    public abstract SyteResult<BoundsResult> getBounds(UrlImageSearch urlImageSearch);

    /**
     * Retrieves items.
     * Synchronous method. Must NOT be called on the UI thread!
     * @param bound {@link Bound}. The list of Bounds can be retrieved from the result of getBounds call.
     * @param cropCoordinates pass {@link CropCoordinates} to enable crop. Pass null to disregard crop.
     * @return {@link SyteResult}
     */
    public abstract SyteResult<ItemsResult> getItemsForBound(Bound bound, CropCoordinates cropCoordinates);

    /**
     * Retrieves items.
     * Asynchronous equivalent for the {@link #getItemsForBound(Bound, CropCoordinates)} method.
     * @param bound {@link Bound}. The list of Bounds can be retrieved from the result of getBounds call.
     * @param cropCoordinates pass {@link CropCoordinates} to enable crop. Pass null to disregard crop.
     * @param callback {@link SyteCallback}
     */
    public abstract void getItemsForBoundAsync(Bound bound, CropCoordinates cropCoordinates, SyteCallback<ItemsResult> callback);

    /**
     * Retrieves bounds
     * Asynchronous equivalent for the {@link #getBounds(Context, ImageSearch)} method.
     * @param context context
     * @param imageSearch {@link ImageSearch}
     * @param callback {@link SyteCallback}
     */
    public abstract void getBoundsAsync(Context context, ImageSearch imageSearch, SyteCallback<BoundsResult> callback);

    /**
     * Retrieves bounds
     * Asynchronous equivalent for the {@link #getBounds(UrlImageSearch)} method.
     * @param urlImageSearch {@link UrlImageSearch}
     * @param callback {@link SyteCallback}
     */
    public abstract void getBoundsAsync(UrlImageSearch urlImageSearch, SyteCallback<BoundsResult> callback);

    /**
     * Make 'Similars' call
     * Synchronous method. Must NOT be called on the UI thread!
     * @param similarProducts {@link SimilarItems}
     * @return {@link SyteResult}
     */
    public abstract SyteResult<SimilarProductsResult> getSimilarProducts(
            SimilarItems similarProducts
    );

    /**
     * Make 'Similars' call
     * Asynchronous equivalent for the {@link #getSimilarProducts(SimilarItems)} method.
     * @param similarProducts {@link SimilarItems}
     * @param callback {@link SyteCallback}
     */
    public abstract void getSimilarProductsAsync(
            SimilarItems similarProducts,
            SyteCallback<SimilarProductsResult> callback
    );

    /**
     * Make 'Shop the look' call
     * Synchronous method. Must NOT be called on the UI thread!
     * @param shopTheLook {@link ShopTheLook}
     * @return {@link SyteResult}
     */
    public abstract SyteResult<ShopTheLookResult> getShopTheLook(
            ShopTheLook shopTheLook
    );

    /**
     * Make 'Shop the look' call
     * Asynchronous equivalent for the {@link #getShopTheLook(ShopTheLook)} method.
     * @param shopTheLook {@link ShopTheLook}
     * @param callback {@link SyteCallback}
     */
    public abstract void getShopTheLookAsync(
            ShopTheLook shopTheLook,
            SyteCallback<ShopTheLookResult> callback
    );

    /**
     * Make 'Personalization' call
     * Synchronous method. Must NOT be called on the UI thread!
     * @param personalization {@link Personalization}
     * @return {@link SyteResult}
     */
    public abstract SyteResult<PersonalizationResult> getPersonalizedProducts(
            Personalization personalization
    );

    /**
     * Make 'Personalization' call
     * Asynchronous equivalent for the {@link #getPersonalizedProducts(Personalization)} method.
     * @param personalization {@link Personalization}
     * @param callback {@link SyteCallback}
     */
    public abstract void getPersonalizedProductsAsync(
            Personalization personalization,
            SyteCallback<PersonalizationResult> callback
    );

    /**
     * Retrieves the most popular searches for the specified language.
     * Synchronous method. Must NOT be called on the UI thread.
     * @param lang locale to retrieve the searches for.
     * @return The list of the most popular searches.
     */
    public abstract SyteResult<List<String>> getPopularSearch(String lang);

    /**
     * Retrieves the most popular searches for the specified language.
     * Asynchronous equivalent of the {@link #getPopularSearch(String)} method.
     * @param lang locale to retrieve the searches for.
     * @param callback instance of {@link SyteCallback}.
     */
    public abstract void getPopularSearchAsync(String lang, SyteCallback<List<String>> callback);

    /**
     * Retrieves the text search results.
     * Synchronous method. Must NOT be called on the UI thread.
     * @param textSearch query parameters {@link TextSearch}
     * @return {@link SyteResult}
     */
    public abstract SyteResult<TextSearchResult> getTextSearch(TextSearch textSearch);

    /**
     * Retrieves the text search results.
     * Asynchronous equivalent of the {@link #getTextSearch(TextSearch)} method.
     * @param textSearch query parameters {@link TextSearch}
     * @param callback {@link SyteResult}
     */
    public abstract void getTextSearchAsync(TextSearch textSearch, SyteCallback<TextSearchResult> callback);

    /**
     * Retrieves Auto-complete results.
     * This method is asynchronous.
     * There must be at least 500ms interval between calls to this method.
     * Otherwise there are two options:
     * 1. If {@link com.syte.ai.android_sdk.core.SyteConfiguration#setAllowAutoCompletionQueue(boolean)}
     *    is set to true, the last request data will be saved in a queue and the
     *    request will be fired after 500ms.
     * 2. If {@link com.syte.ai.android_sdk.core.SyteConfiguration#setAllowAutoCompletionQueue(boolean)}
     *    is set to false, the calls to this method made within 500ms will be ignored.
     * @param query text query
     * @param lang locale to retrieve the searches for.
     * @param callback {@link SyteCallback}
     */
    public abstract void getAutoComplete(
            String query,
            String lang,
            SyteCallback<AutoCompleteResult> callback
    );

    /**
     * Retrieves Auto-complete results. Will use the locale set in {@link SyteConfiguration}.
     * This method is asynchronous.
     * There must be at least 500ms interval between calls to this method.
     * Otherwise there are two options:
     * 1. If {@link com.syte.ai.android_sdk.core.SyteConfiguration#setAllowAutoCompletionQueue(boolean)}
     *    is set to true, the last request data will be saved in a queue and the
     *    request will be fired after 500ms.
     * 2. If {@link com.syte.ai.android_sdk.core.SyteConfiguration#setAllowAutoCompletionQueue(boolean)}
     *    is set to false, the calls to this method made within 500ms will be ignored.
     * @param query text query
     * @param callback {@link SyteCallback}
     */
    public abstract void getAutoComplete(String query, SyteCallback<AutoCompleteResult> callback);
}

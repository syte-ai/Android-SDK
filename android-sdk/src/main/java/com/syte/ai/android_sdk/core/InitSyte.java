package com.syte.ai.android_sdk.core;

import com.syte.ai.android_sdk.ImageSearchClient;
import com.syte.ai.android_sdk.ProductRecommendationClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.TextSearchClient;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.exceptions.SyteWrongInputException;
import com.syte.ai.android_sdk.util.SyteLogger;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;


public abstract class InitSyte {

    /**
     * Creates a new instance of the InitSyte class.
     * @return new instance
     */
    public static InitSyte newInstance() {
        return new InitSyteImpl();
    }

    /**
     * Starts the user session.
     * Synchronous method. Must NOT be called on the UI thread!
     * NOTE:    either this method or {@link #startSessionAsync(SyteConfiguration, SyteCallback)}
     *          MUST be called before any other methods in this class!
     * @param configuration {@link SyteConfiguration}
     * @return Result indicating whether the session was started successfully.
     */
    public abstract SyteResult<Boolean> startSession(SyteConfiguration configuration);

    /**
     * Starts the user session.
     * NOTE:    either this method or {@link #startSession(SyteConfiguration)}
     *          MUST be called before any other methods in this class!
     * @param configuration {@link SyteConfiguration}
     * @param callback callback with result. Is invoked on the UI thread.
     */
    public abstract void startSessionAsync(SyteConfiguration configuration, SyteCallback<Boolean> callback);

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
     * Getter for {@link SytePlatformSettings}. Method will return null if the session was not started!
     * @return {@link SytePlatformSettings}
     */
    @Nullable
    public abstract SytePlatformSettings getSytePlatformSettings();

    /**
     * Build and get a {@link ProductRecommendationClient} instance.
     * @return {@link ProductRecommendationClient}
     */
    public abstract ProductRecommendationClient getProductRecommendationClient();

    /**
     * Build and get a {@link ImageSearchClient} instance.
     * @return {@link ImageSearchClient}
     */
    public abstract ImageSearchClient getImageSearchClient();

    public abstract TextSearchClient getTextSearchClient();

    /**
     * Uninitialize Syte and force reset session ID.
     * If not called, the session ID will be automatically reset if no requests were sent within 30 minutes.
     */
    public abstract void endSession();

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

    public abstract List<String> getResentTextSearches();

    /**
     * Set log level.
     * @param logLevel log level to be set
     */
    public abstract void setLogLevel(SyteLogger.LogLevel logLevel);

}

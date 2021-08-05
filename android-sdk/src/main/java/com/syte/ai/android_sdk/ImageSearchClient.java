package com.syte.ai.android_sdk;

import android.content.Context;

import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.ImageSearch;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.UrlImageSearch;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.ItemsResult;

/**
 * Class responsible for Image Search functionality.
 * The *getBounds(..)* methods are used to retrieve the items on the image (with their coordinates)
 * recognized by Syte API. Call one to retrieve the list of bounds and pass one of them to either
 * *getItemsForBound(..)* or *getItemsForBoundAsync(..)* methods to get the list of related items.
 */
public interface ImageSearchClient {

    /**
     * Retrieves bounds
     * Synchronous method. Must NOT be called on the UI thread!
     * @param context context
     * @param imageSearch {@link ImageSearch}
     * @return {@link SyteResult}
     */
    SyteResult<BoundsResult> getBounds(Context context, ImageSearch imageSearch);

    /**
     * Retrieves bounds
     * Synchronous method. Must NOT be called on the UI thread!
     * @param urlImageSearch {@link UrlImageSearch}
     * @return {@link SyteResult}
     */
    SyteResult<BoundsResult> getBounds(UrlImageSearch urlImageSearch);

    /**
     * Retrieves items.
     * Synchronous method. Must NOT be called on the UI thread!
     * @param bound {@link Bound}. The list of Bounds can be retrieved from the result of getBounds call.
     * @param cropCoordinates pass {@link CropCoordinates} to enable crop. Pass null to disregard crop.
     * @return {@link SyteResult}
     */
    SyteResult<ItemsResult> getItemsForBound(Bound bound, CropCoordinates cropCoordinates);

    /**
     * Retrieves items.
     * Asynchronous equivalent for the {@link #getItemsForBound(Bound, CropCoordinates)} method.
     * @param bound {@link Bound}. The list of Bounds can be retrieved from the result of getBounds call.
     * @param cropCoordinates pass {@link CropCoordinates} to enable crop. Pass null to disregard crop.
     * @param callback {@link SyteCallback}
     */
    void getItemsForBoundAsync(Bound bound, CropCoordinates cropCoordinates, SyteCallback<ItemsResult> callback);

    /**
     * Retrieves bounds
     * Asynchronous equivalent for the {@link #getBounds(Context, ImageSearch)} method.
     * @param context context
     * @param imageSearch {@link ImageSearch}
     * @param callback {@link SyteCallback}
     */
    void getBoundsAsync(Context context, ImageSearch imageSearch, SyteCallback<BoundsResult> callback);

    /**
     * Retrieves bounds
     * Asynchronous equivalent for the {@link #getBounds(UrlImageSearch)} method.
     * @param urlImageSearch {@link UrlImageSearch}
     * @param callback {@link SyteCallback}
     */
    void getBoundsAsync(UrlImageSearch urlImageSearch, SyteCallback<BoundsResult> callback);

}

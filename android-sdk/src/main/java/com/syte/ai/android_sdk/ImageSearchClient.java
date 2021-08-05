package com.syte.ai.android_sdk;

import android.content.Context;

import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.ImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.UrlImageSearchRequestData;
import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;

/**
 * Class responsible for Image Search functionality.
 * The *getBounds(..)* methods are used to retrieve the items on the image (with their coordinates)
 * recognized by Syte API. Call one to retrieve the list of bounds and pass one of them to one of the
 * *getOffers(..)* methods to get the list of related offers.
 */
public interface ImageSearchClient {

    /**
     * Retrieves bounds
     * Synchronous method. Must NOT be called on the UI thread!
     * @param context context
     * @param imageSearchRequestData {@link ImageSearchRequestData}
     * @return {@link SyteResult}
     */
    SyteResult<BoundsResult> getBounds(Context context, ImageSearchRequestData imageSearchRequestData);

    /**
     * Retrieves bounds
     * Synchronous method. Must NOT be called on the UI thread!
     * @param urlImageSearchRequestData {@link UrlImageSearchRequestData}
     * @return {@link SyteResult}
     */
    SyteResult<BoundsResult> getBounds(UrlImageSearchRequestData urlImageSearchRequestData);

    /**
     * Retrieves offers
     * Synchronous method. Must NOT be called on the UI thread!
     * @param bound {@link Bound}. The list of Bounds can be retrieved from the result of getBounds call.
     * @param cropCoordinates pass {@link CropCoordinates} to enable crop. Pass null to disregard crop.
     * @return {@link SyteResult}
     */
    SyteResult<OffersResult> getOffers(Bound bound, CropCoordinates cropCoordinates);

    /**
     * Retrieves offers
     * Asynchronous equivalent for the {@link #getOffers(Bound, CropCoordinates)} method.
     * @param bound {@link Bound}. The list of Bounds can be retrieved from the result of getBounds call.
     * @param cropCoordinates pass {@link CropCoordinates} to enable crop. Pass null to disregard crop.
     * @param callback {@link SyteCallback}
     */
    void getOffersAsync(Bound bound, CropCoordinates cropCoordinates, SyteCallback<OffersResult> callback);

    /**
     * Retrieves bounds
     * Asynchronous equivalent for the {@link #getBounds(Context, ImageSearchRequestData)} method.
     * @param context context
     * @param imageSearchRequestData {@link ImageSearchRequestData}
     * @param callback {@link SyteCallback}
     */
    void getBoundsAsync(Context context, ImageSearchRequestData imageSearchRequestData, SyteCallback<BoundsResult> callback);

    /**
     * Retrieves bounds
     * Asynchronous equivalent for the {@link #getBounds(UrlImageSearchRequestData)} method.
     * @param urlImageSearchRequestData {@link UrlImageSearchRequestData}
     * @param callback {@link SyteCallback}
     */
    void getBoundsAsync(UrlImageSearchRequestData urlImageSearchRequestData, SyteCallback<BoundsResult> callback);

}

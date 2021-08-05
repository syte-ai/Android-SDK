package com.syte.ai.android_sdk.data.result.offers;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.ImageSearch;
import com.syte.ai.android_sdk.data.UrlImageSearch;

/**
 *	A class that represents the return result for all of the *getBounds()* calls in {@link com.syte.ai.android_sdk.ImageSearchClient}.
 */
public class BoundsResult {

	@SerializedName("bounds")
	private List<Bound> bounds = new ArrayList<>();

	private ItemsResult firstBoundItemsResult = null;

	/**
	 * Getter for the returned bounds.
	 * @return list of retrieved bounds.
	 */
	public List<Bound> getBounds() {
		return bounds;
	}

	/**
	 * Set result for the items retrieved automatically for the first bound. Used in the internal implementation.
	 * @param firstBoundItemsResult items for the first bound
	 */
	public void setFirstBoundItemsResult(ItemsResult firstBoundItemsResult) {
		this.firstBoundItemsResult = firstBoundItemsResult;
	}

	/**
	 * Get offers result for the first retrieved bound. Will return null if false is passed
	 * to setRetrieveOffersForTheFirstBound() method in
	 * {@link ImageSearch} or {@link UrlImageSearch}
	 * @return Offers call result for the first retrieved bound.
	 */
	@Nullable
	public ItemsResult getItemsForFirstBound() {
		return firstBoundItemsResult;
	}

}
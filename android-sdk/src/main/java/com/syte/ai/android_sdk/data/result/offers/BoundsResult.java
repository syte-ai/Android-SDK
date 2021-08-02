package com.syte.ai.android_sdk.data.result.offers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

/**
 *	A class that represents the return result for all of the *getBounds()* calls in {@link com.syte.ai.android_sdk.ImageSearchClient}.
 */
public class BoundsResult {

	@SerializedName("bounds")
	private List<Bound> bounds = new ArrayList<>();

	private OffersResult firstBoundOffersResult = null;

	/**
	 * Getter for the returned bounds.
	 * @return list of retrieved bounds.
	 */
	public List<Bound> getBounds() {
		return bounds;
	}

	/**
	 * Set result for the offers retrieved automatically for the first bound. Used in the internal implementation.
	 * @param firstBoundOffersResult offers for the first bound
	 */
	public void setFirstBoundOffersResult(OffersResult firstBoundOffersResult) {
		this.firstBoundOffersResult = firstBoundOffersResult;
	}

	/**
	 * Get offers result for the first retrieved bound. Will return null if false is passed
	 * to setRetrieveOffersForTheFirstBound() method in
	 * {@link com.syte.ai.android_sdk.data.ImageSearchRequestData} or {@link com.syte.ai.android_sdk.data.UrlImageSearchRequestData}
	 * @return Offers call result for the first retrieved bound.
	 */
	@Nullable
	public OffersResult getFirstBoundOffersResult() {
		return firstBoundOffersResult;
	}

}
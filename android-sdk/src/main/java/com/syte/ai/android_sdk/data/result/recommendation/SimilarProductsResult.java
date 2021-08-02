package com.syte.ai.android_sdk.data.result.recommendation;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.result.offers.Offer;

/**
 * A class that represents the result for 'Similars' call
 */
public class SimilarProductsResult{

	@SerializedName("response")
	private List<Offer> data;

	/**
	 * Getter for the list of items
	 * @return list of items
	 */
	public List<Offer> getItems(){
		return data;
	}

}
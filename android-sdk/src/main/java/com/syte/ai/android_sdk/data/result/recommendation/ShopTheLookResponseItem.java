package com.syte.ai.android_sdk.data.result.recommendation;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.result.offers.Offer;

/**
 * A class that represents the Shop the look result item (label + list of items for this label)
 */
public class ShopTheLookResponseItem {

	@SerializedName("ads")
	private List<Offer> offers;

	@SerializedName("label")
	private String label;

	/**
	 * Getter for list of offers
	 * @return list of offers
	 */
	public List<Offer> getOffers(){
		return offers;
	}

	/**
	 * Getter for label
	 * @return label
	 */
	public String getLabel(){
		return label;
	}

}
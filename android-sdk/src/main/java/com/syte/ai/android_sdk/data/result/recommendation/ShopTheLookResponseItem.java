package com.syte.ai.android_sdk.data.result.recommendation;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.result.offers.Item;

/**
 * A class that represents the Shop the look result item (label + list of items for this label)
 */
public class ShopTheLookResponseItem {

	@SerializedName("ads")
	private List<Item> items;

	@SerializedName("label")
	private String label;

	/**
	 * Getter for list of items
	 * @return list of items
	 */
	public List<Item> getItems(){
		return items;
	}

	/**
	 * Getter for label
	 * @return label
	 */
	public String getLabel(){
		return label;
	}

}
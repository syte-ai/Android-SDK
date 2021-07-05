package com.syte.ai.android_sdk.data.result.recommendation;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.result.offers.Offer;

public class ShopTheLookResponseItem {

	@SerializedName("ads")
	private List<Offer> offers;

	@SerializedName("label")
	private String label;

	public List<Offer> getOffers(){
		return offers;
	}

	public String getLabel(){
		return label;
	}

	@Override
 	public String toString(){
		return 
			"ResponseItem{" + 
			"ads = '" + offers + '\'' +
			",label = '" + label + '\'' + 
			"}";
		}
}
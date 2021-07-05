package com.syte.ai.android_sdk.data.result.recommendation;

import java.util.List;
import com.google.gson.annotations.SerializedName;
import com.syte.ai.android_sdk.data.result.offers.Offer;

public class SimilarProductsResult{

	@SerializedName("response")
	private List<Offer> data;

	public List<Offer> getSimilars(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"SimilarProductsResult{" + 
			"data = '" + data + '\'' +
			"}";
		}
}
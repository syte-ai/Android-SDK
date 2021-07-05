package com.syte.ai.android_sdk.data.result.recommendation;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ShopTheLookResult {

	@SerializedName("response")
	private List<ShopTheLookResponseItem> items;

	@SerializedName("fallback")
	private String fallback;

	public List<ShopTheLookResponseItem> getItems(){
		return items;
	}

	public String getFallback(){
		return fallback;
	}

	@Override
 	public String toString(){
		return 
			"ShopTheLookResult{" +
			"response = '" + items + '\'' +
			",fallback = '" + fallback + '\'' + 
			"}";
		}
}
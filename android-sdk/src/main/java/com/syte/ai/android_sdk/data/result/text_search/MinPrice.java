package com.syte.ai.android_sdk.data.result.text_search;

import com.google.gson.annotations.SerializedName;

public class MinPrice{

	@SerializedName("display_name")
	private String displayName;

	@SerializedName("value")
	private double value;

	@SerializedName("order")
	private int order;

	public String getDisplayName(){
		return displayName;
	}

	public double getValue(){
		return value;
	}

	public int getOrder(){
		return order;
	}
}
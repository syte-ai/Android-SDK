package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class WeightsItem{

	@SerializedName("field")
	private String field;

	@SerializedName("weight")
	private int weight;

	@SerializedName("display_name")
	private String displayName;

	@SerializedName("enabled")
	private boolean enabled;

	@SerializedName("order")
	private String order;

	public String getField(){
		return field;
	}

	public int getWeight(){
		return weight;
	}

	public String getDisplayName(){
		return displayName;
	}

	public boolean isEnabled(){
		return enabled;
	}

	public String getOrder(){
		return order;
	}

	@Override
 	public String toString(){
		return 
			"WeightsItem{" + 
			"field = '" + field + '\'' + 
			",weight = '" + weight + '\'' + 
			",display_name = '" + displayName + '\'' + 
			",enabled = '" + enabled + '\'' + 
			",order = '" + order + '\'' + 
			"}";
		}
}
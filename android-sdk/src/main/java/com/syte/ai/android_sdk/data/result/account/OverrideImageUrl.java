package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class OverrideImageUrl{

	@SerializedName("active")
	private boolean active;

	@SerializedName("selector")
	private String selector;

	@SerializedName("attribute")
	private String attribute;

	public boolean isActive(){
		return active;
	}

	public String getSelector(){
		return selector;
	}

	public String getAttribute(){
		return attribute;
	}

	@Override
 	public String toString(){
		return 
			"OverrideImageUrl{" + 
			"active = '" + active + '\'' + 
			",selector = '" + selector + '\'' + 
			",attribute = '" + attribute + '\'' + 
			"}";
		}
}
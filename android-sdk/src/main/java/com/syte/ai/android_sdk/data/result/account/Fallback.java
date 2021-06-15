package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Fallback{

	@SerializedName("fallback_method")
	private String fallbackMethod;

	@SerializedName("is_fallback_activated")
	private boolean isFallbackActivated;

	public String getFallbackMethod(){
		return fallbackMethod;
	}

	public boolean isIsFallbackActivated(){
		return isFallbackActivated;
	}

	@Override
 	public String toString(){
		return 
			"Fallback{" + 
			"fallback_method = '" + fallbackMethod + '\'' + 
			",is_fallback_activated = '" + isFallbackActivated + '\'' + 
			"}";
		}
}
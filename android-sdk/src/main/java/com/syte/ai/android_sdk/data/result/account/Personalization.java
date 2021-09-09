package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Personalization{

	@SerializedName("fallback")
	private Fallback fallback;

	@SerializedName("model_version")
	private String modelVersion;

	@SerializedName("active")
	private boolean active;

	@SerializedName("takeSkuFromPageviewEvents")
	private boolean takeSkuFromPageviewEvents;

	public Fallback getFallback(){
		return fallback;
	}

	public String getModelVersion(){
		return modelVersion;
	}

	public boolean isActive(){
		return active;
	}

	public boolean isTakeSkuFromPageviewEvents(){
		return takeSkuFromPageviewEvents;
	}

	@Override
 	public String toString(){
		return 
			"Personalization{" + 
			"fallback = '" + fallback + '\'' + 
			",model_version = '" + modelVersion + '\'' + 
			",active = '" + active + '\'' + 
			",takeSkuFromPageviewEvents = '" + takeSkuFromPageviewEvents + '\'' + 
			"}";
		}
}
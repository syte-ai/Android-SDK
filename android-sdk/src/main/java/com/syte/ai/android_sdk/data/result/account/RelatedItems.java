package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class RelatedItems{

	@SerializedName("active")
	private boolean active;

	@SerializedName("imageSelector")
	private String imageSelector;

	public boolean isActive(){
		return active;
	}

	public String getImageSelector(){
		return imageSelector;
	}

	@Override
 	public String toString(){
		return 
			"RelatedItems{" + 
			"active = '" + active + '\'' + 
			",imageSelector = '" + imageSelector + '\'' + 
			"}";
		}
}
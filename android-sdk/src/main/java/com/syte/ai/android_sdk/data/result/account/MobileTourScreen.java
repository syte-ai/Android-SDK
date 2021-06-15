package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class MobileTourScreen{

	@SerializedName("useInspoGallery")
	private boolean useInspoGallery;

	@SerializedName("active")
	private boolean active;

	public boolean isUseInspoGallery(){
		return useInspoGallery;
	}

	public boolean isActive(){
		return active;
	}

	@Override
 	public String toString(){
		return 
			"MobileTourScreen{" + 
			"useInspoGallery = '" + useInspoGallery + '\'' + 
			",active = '" + active + '\'' + 
			"}";
		}
}
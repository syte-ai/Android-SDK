package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class SimilarItems{

	@SerializedName("offerNavBehaviourDesktop")
	private String offerNavBehaviourDesktop;

	@SerializedName("active")
	private boolean active;

	@SerializedName("offerNavBehaviourMobile")
	private String offerNavBehaviourMobile;

	@SerializedName("shouldNotUseFallbackImages")
	private boolean shouldNotUseFallbackImages;

	public String getOfferNavBehaviourDesktop(){
		return offerNavBehaviourDesktop;
	}

	public boolean isActive(){
		return active;
	}

	public String getOfferNavBehaviourMobile(){
		return offerNavBehaviourMobile;
	}

	public boolean isShouldNotUseFallbackImages(){
		return shouldNotUseFallbackImages;
	}

	@Override
 	public String toString(){
		return 
			"SimilarItems{" + 
			"offerNavBehaviourDesktop = '" + offerNavBehaviourDesktop + '\'' + 
			",active = '" + active + '\'' + 
			",offerNavBehaviourMobile = '" + offerNavBehaviourMobile + '\'' + 
			",shouldNotUseFallbackImages = '" + shouldNotUseFallbackImages + '\'' + 
			"}";
		}
}
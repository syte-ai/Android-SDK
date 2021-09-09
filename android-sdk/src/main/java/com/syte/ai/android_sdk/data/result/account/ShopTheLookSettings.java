package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class ShopTheLookSettings {

	@SerializedName("zip")
	private boolean zip;

	@SerializedName("getOriginalBound")
	private boolean getOriginalBound;

	@SerializedName("shouldNotFetchSimilars")
	private boolean shouldNotFetchSimilars;

	@SerializedName("active")
	private boolean active;

	@SerializedName("shouldNotUseFallbackImages")
	private boolean shouldNotUseFallbackImages;

	public boolean isZip(){
		return zip;
	}

	public boolean isGetOriginalBound(){
		return getOriginalBound;
	}

	public boolean isShouldNotFetchSimilars(){
		return shouldNotFetchSimilars;
	}

	public boolean isActive(){
		return active;
	}

	public boolean isShouldNotUseFallbackImages(){
		return shouldNotUseFallbackImages;
	}

	@Override
 	public String toString(){
		return 
			"ShopTheLook{" + 
			"zip = '" + zip + '\'' + 
			",getOriginalBound = '" + getOriginalBound + '\'' + 
			",shouldNotFetchSimilars = '" + shouldNotFetchSimilars + '\'' + 
			",active = '" + active + '\'' + 
			",shouldNotUseFallbackImages = '" + shouldNotUseFallbackImages + '\'' + 
			"}";
		}
}
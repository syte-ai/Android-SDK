package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Theme{

	@SerializedName("iconHoverAnimation")
	private IconHoverAnimation iconHoverAnimation;

	public IconHoverAnimation getIconHoverAnimation(){
		return iconHoverAnimation;
	}

	@Override
 	public String toString(){
		return 
			"Theme{" + 
			"iconHoverAnimation = '" + iconHoverAnimation + '\'' + 
			"}";
		}
}
package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class IconHoverAnimation{

	@SerializedName("auto")
	private boolean auto;

	@SerializedName("show")
	private boolean show;

	public boolean isAuto(){
		return auto;
	}

	public boolean isShow(){
		return show;
	}

	@Override
 	public String toString(){
		return 
			"IconHoverAnimation{" + 
			"auto = '" + auto + '\'' + 
			",show = '" + show + '\'' + 
			"}";
		}
}
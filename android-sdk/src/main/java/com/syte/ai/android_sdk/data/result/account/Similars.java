package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Similars{

	@SerializedName("use_bb_tags")
	private boolean useBbTags;

	public boolean isUseBbTags(){
		return useBbTags;
	}

	@Override
 	public String toString(){
		return 
			"Similars{" + 
			"use_bb_tags = '" + useBbTags + '\'' + 
			"}";
		}
}
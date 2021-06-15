package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class CollaborativeFiltering{

	@SerializedName("active")
	private boolean active;

	public boolean isActive(){
		return active;
	}

	@Override
 	public String toString(){
		return 
			"CollaborativeFiltering{" + 
			"active = '" + active + '\'' + 
			"}";
		}
}
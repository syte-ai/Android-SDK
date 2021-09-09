package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class SessionBased{

	@SerializedName("active")
	private boolean active;

	public boolean isActive(){
		return active;
	}

	@Override
 	public String toString(){
		return 
			"SessionBased{" + 
			"active = '" + active + '\'' + 
			"}";
		}
}
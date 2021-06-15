package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Brand{

	@SerializedName("field")
	private String field;

	@SerializedName("displayName")
	private String displayName;

	@SerializedName("enabled")
	private boolean enabled;

	public String getField(){
		return field;
	}

	public String getDisplayName(){
		return displayName;
	}

	public boolean isEnabled(){
		return enabled;
	}

	@Override
 	public String toString(){
		return 
			"Brand{" + 
			"field = '" + field + '\'' + 
			",displayName = '" + displayName + '\'' + 
			",enabled = '" + enabled + '\'' + 
			"}";
		}
}
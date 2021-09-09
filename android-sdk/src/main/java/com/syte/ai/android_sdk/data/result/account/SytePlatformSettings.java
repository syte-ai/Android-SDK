package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class SytePlatformSettings {

	@SerializedName("data")
	private Data data;

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"AccountDataService{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}
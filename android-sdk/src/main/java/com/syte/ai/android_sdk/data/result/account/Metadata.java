package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Metadata{

	@SerializedName("lastUpdatedBy")
	private int lastUpdatedBy;

	@SerializedName("lastUpdatedOn")
	private String lastUpdatedOn;

	public int getLastUpdatedBy(){
		return lastUpdatedBy;
	}

	public String getLastUpdatedOn(){
		return lastUpdatedOn;
	}

	@Override
 	public String toString(){
		return 
			"Metadata{" + 
			"lastUpdatedBy = '" + lastUpdatedBy + '\'' + 
			",lastUpdatedOn = '" + lastUpdatedOn + '\'' + 
			"}";
		}
}
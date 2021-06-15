package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class BestSellers{

	@SerializedName("dataFrame")
	private int dataFrame;

	@SerializedName("active")
	private boolean active;

	public int getDataFrame(){
		return dataFrame;
	}

	public boolean isActive(){
		return active;
	}

	@Override
 	public String toString(){
		return 
			"BestSellers{" + 
			"dataFrame = '" + dataFrame + '\'' + 
			",active = '" + active + '\'' + 
			"}";
		}
}
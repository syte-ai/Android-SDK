package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class CrossProductFeatures{

	@SerializedName("resultsSortingOptions")
	private ResultsSortingOptions resultsSortingOptions;

	public ResultsSortingOptions getResultsSortingOptions(){
		return resultsSortingOptions;
	}

	@Override
 	public String toString(){
		return 
			"CrossProductFeatures{" + 
			"resultsSortingOptions = '" + resultsSortingOptions + '\'' + 
			"}";
		}
}
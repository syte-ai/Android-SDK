package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Components{

	@SerializedName("topSearch")
	private TopSearch topSearch;

	@SerializedName("filterSearch")
	private FilterSearch filterSearch;

	public TopSearch getTopSearch(){
		return topSearch;
	}

	public FilterSearch getFilterSearch(){
		return filterSearch;
	}

	@Override
 	public String toString(){
		return 
			"Components{" + 
			"topSearch = '" + topSearch + '\'' + 
			",filterSearch = '" + filterSearch + '\'' + 
			"}";
		}
}
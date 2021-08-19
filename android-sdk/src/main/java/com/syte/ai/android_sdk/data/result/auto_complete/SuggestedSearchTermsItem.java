package com.syte.ai.android_sdk.data.result.auto_complete;

import com.google.gson.annotations.SerializedName;

public class SuggestedSearchTermsItem{

	@SerializedName("popularity")
	private int popularity;

	@SerializedName("search_term")
	private String searchTerm;

	public int getPopularity(){
		return popularity;
	}

	public String getSearchTerm(){
		return searchTerm;
	}
}
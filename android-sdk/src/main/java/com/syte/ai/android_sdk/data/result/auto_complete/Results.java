package com.syte.ai.android_sdk.data.result.auto_complete;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Results{

	@SerializedName("allResults")
	private String allResults;

	@SerializedName("suggestedItems")
	private List<SuggestedItem> suggestedItems;

	@SerializedName("suggestedCategories")
	private List<String> suggestedCategories;

	@SerializedName("suggestedSearchTerms")
	private List<SuggestedSearchTermsItem> suggestedSearchTerms;

	public String getAllResults(){
		return allResults;
	}

	public List<SuggestedItem> getSuggestedItems(){
		return suggestedItems;
	}

	public List<String> getSuggestedCategories(){
		return suggestedCategories;
	}

	public List<SuggestedSearchTermsItem> getSuggestedSearchTerms(){
		return suggestedSearchTerms;
	}
}
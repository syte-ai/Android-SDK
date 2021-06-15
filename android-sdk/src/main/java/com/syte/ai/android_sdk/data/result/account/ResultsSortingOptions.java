package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class ResultsSortingOptions{

	@SerializedName("views_count")
	private ViewsCount viewsCount;

	@SerializedName("relevance")
	private Relevance relevance;

	@SerializedName("bestSellers")
	private BestSellers bestSellers;

	public ViewsCount getViewsCount(){
		return viewsCount;
	}

	public Relevance getRelevance(){
		return relevance;
	}

	public BestSellers getBestSellers(){
		return bestSellers;
	}

	@Override
 	public String toString(){
		return 
			"ResultsSortingOptions{" + 
			"views_count = '" + viewsCount + '\'' + 
			",relevance = '" + relevance + '\'' + 
			",bestSellers = '" + bestSellers + '\'' + 
			"}";
		}
}
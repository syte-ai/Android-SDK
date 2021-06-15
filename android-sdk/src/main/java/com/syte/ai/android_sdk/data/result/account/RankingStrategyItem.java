package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RankingStrategyItem{

	@SerializedName("product")
	private String product;

	@SerializedName("weights")
	private List<WeightsItem> weights;

	public String getProduct(){
		return product;
	}

	public List<WeightsItem> getWeights(){
		return weights;
	}

	@Override
 	public String toString(){
		return 
			"RankingStrategyItem{" + 
			"product = '" + product + '\'' + 
			",weights = '" + weights + '\'' + 
			"}";
		}
}
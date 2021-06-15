package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Api{

	@SerializedName("merchandiseRules")
	private List<MerchandiseRulesItem> merchandiseRules;

	@SerializedName("hide_missing_labels")
	private boolean hideMissingLabels;

	@SerializedName("rankingStrategy")
	private List<RankingStrategyItem> rankingStrategy;

	@SerializedName("allowed_domains")
	private List<Object> allowedDomains;

	@SerializedName("v1_1")
	private V11 v11;

	public List<MerchandiseRulesItem> getMerchandiseRules(){
		return merchandiseRules;
	}

	public boolean isHideMissingLabels(){
		return hideMissingLabels;
	}

	public List<RankingStrategyItem> getRankingStrategy(){
		return rankingStrategy;
	}

	public List<Object> getAllowedDomains(){
		return allowedDomains;
	}

	public V11 getV11(){
		return v11;
	}

	@Override
 	public String toString(){
		return 
			"Api{" + 
			"merchandiseRules = '" + merchandiseRules + '\'' + 
			",hide_missing_labels = '" + hideMissingLabels + '\'' + 
			",rankingStrategy = '" + rankingStrategy + '\'' + 
			",allowed_domains = '" + allowedDomains + '\'' + 
			",v1_1 = '" + v11 + '\'' + 
			"}";
		}
}
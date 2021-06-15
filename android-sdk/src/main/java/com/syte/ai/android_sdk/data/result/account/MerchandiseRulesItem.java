package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MerchandiseRulesItem{

	@SerializedName("subRules")
	private List<SubRulesItem> subRules;

	@SerializedName("product")
	private String product;

	@SerializedName("metadata")
	private Metadata metadata;

	@SerializedName("searchCondition")
	private SearchCondition searchCondition;

	@SerializedName("name")
	private String name;

	@SerializedName("active")
	private boolean active;

	@SerializedName("weight")
	private int weight;

	@SerializedName("action")
	private String action;

	@SerializedName("id")
	private String id;

	@SerializedName("sourceCondition")
	private List<SourceConditionItem> sourceCondition;

	public List<SubRulesItem> getSubRules(){
		return subRules;
	}

	public String getProduct(){
		return product;
	}

	public Metadata getMetadata(){
		return metadata;
	}

	public SearchCondition getSearchCondition(){
		return searchCondition;
	}

	public String getName(){
		return name;
	}

	public boolean isActive(){
		return active;
	}

	public int getWeight(){
		return weight;
	}

	public String getAction(){
		return action;
	}

	public String getId(){
		return id;
	}

	public List<SourceConditionItem> getSourceCondition(){
		return sourceCondition;
	}

	@Override
 	public String toString(){
		return 
			"MerchandiseRulesItem{" + 
			"subRules = '" + subRules + '\'' + 
			",product = '" + product + '\'' + 
			",metadata = '" + metadata + '\'' + 
			",searchCondition = '" + searchCondition + '\'' + 
			",name = '" + name + '\'' + 
			",active = '" + active + '\'' + 
			",weight = '" + weight + '\'' + 
			",action = '" + action + '\'' + 
			",id = '" + id + '\'' + 
			",sourceCondition = '" + sourceCondition + '\'' + 
			"}";
		}
}
package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubRulesItem{

	@SerializedName("field")
	private String field;

	@SerializedName("values")
	private List<String> values;

	@SerializedName("platformSubType")
	private String platformSubType;

	@SerializedName("subType")
	private String subType;

	@SerializedName("type")
	private String type;

	@SerializedName("enabled")
	private boolean enabled;

	public String getField(){
		return field;
	}

	public List<String> getValues(){
		return values;
	}

	public String getPlatformSubType(){
		return platformSubType;
	}

	public String getSubType(){
		return subType;
	}

	public String getType(){
		return type;
	}

	public boolean isEnabled(){
		return enabled;
	}

	@Override
 	public String toString(){
		return 
			"SubRulesItem{" + 
			"field = '" + field + '\'' + 
			",values = '" + values + '\'' + 
			",platformSubType = '" + platformSubType + '\'' + 
			",subType = '" + subType + '\'' + 
			",type = '" + type + '\'' + 
			",enabled = '" + enabled + '\'' + 
			"}";
		}
}
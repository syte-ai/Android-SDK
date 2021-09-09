package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SourceConditionItem{

	@SerializedName("values")
	private List<Object> values;

	@SerializedName("type")
	private String type;

	@SerializedName("enabled")
	private boolean enabled;

	public List<Object> getValues(){
		return values;
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
			"SourceConditionItem{" + 
			"values = '" + values + '\'' + 
			",type = '" + type + '\'' + 
			",enabled = '" + enabled + '\'' + 
			"}";
		}
}
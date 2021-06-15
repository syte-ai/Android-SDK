package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SearchCondition{

	@SerializedName("terms")
	private List<String> terms;

	@SerializedName("enabled")
	private boolean enabled;

	public List<String> getTerms(){
		return terms;
	}

	public boolean isEnabled(){
		return enabled;
	}

	@Override
 	public String toString(){
		return 
			"SearchCondition{" + 
			"terms = '" + terms + '\'' + 
			",enabled = '" + enabled + '\'' + 
			"}";
		}
}
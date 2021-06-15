package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class AssociationRules{

	@SerializedName("active")
	private boolean active;

	public boolean isActive(){
		return active;
	}

	@Override
 	public String toString(){
		return 
			"AssociationRules{" + 
			"active = '" + active + '\'' + 
			"}";
		}
}
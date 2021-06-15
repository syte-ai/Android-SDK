package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class FeedsDefaults{

	@SerializedName("defaultFeed")
	private String defaultFeed;

	@SerializedName("defaultCatalog")
	private String defaultCatalog;

	public String getDefaultFeed(){
		return defaultFeed;
	}

	public String getDefaultCatalog(){
		return defaultCatalog;
	}

	@Override
 	public String toString(){
		return 
			"FeedsDefaults{" + 
			"defaultFeed = '" + defaultFeed + '\'' + 
			",defaultCatalog = '" + defaultCatalog + '\'' + 
			"}";
		}
}
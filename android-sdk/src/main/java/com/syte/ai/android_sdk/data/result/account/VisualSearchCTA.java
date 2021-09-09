package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class VisualSearchCTA{

	@SerializedName("on_focus")
	private boolean onFocus;

	@SerializedName("header")
	private boolean header;

	@SerializedName("no_results")
	private boolean noResults;

	public boolean isOnFocus(){
		return onFocus;
	}

	public boolean isHeader(){
		return header;
	}

	public boolean isNoResults(){
		return noResults;
	}

	@Override
 	public String toString(){
		return 
			"VisualSearchCTA{" + 
			"on_focus = '" + onFocus + '\'' + 
			",header = '" + header + '\'' + 
			",no_results = '" + noResults + '\'' + 
			"}";
		}
}
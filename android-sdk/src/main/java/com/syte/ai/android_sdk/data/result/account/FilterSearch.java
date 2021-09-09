package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class FilterSearch{

	@SerializedName("itemsPerPage")
	private int itemsPerPage;

	@SerializedName("active")
	private boolean active;

	@SerializedName("customCSS")
	private String customCSS;

	public int getItemsPerPage(){
		return itemsPerPage;
	}

	public boolean isActive(){
		return active;
	}

	public String getCustomCSS(){
		return customCSS;
	}

	@Override
 	public String toString(){
		return 
			"FilterSearch{" + 
			"itemsPerPage = '" + itemsPerPage + '\'' + 
			",active = '" + active + '\'' + 
			",customCSS = '" + customCSS + '\'' + 
			"}";
		}
}
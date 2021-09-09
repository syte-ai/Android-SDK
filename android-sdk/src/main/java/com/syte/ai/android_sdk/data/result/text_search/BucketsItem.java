package com.syte.ai.android_sdk.data.result.text_search;

import com.google.gson.annotations.SerializedName;

public class BucketsItem{

	@SerializedName("doc_count")
	private int docCount;

	@SerializedName("display_name")
	private String displayName;

	@SerializedName("key")
	private String key;

	public int getDocCount(){
		return docCount;
	}

	public String getDisplayName(){
		return displayName;
	}

	public String getKey(){
		return key;
	}
}
package com.syte.ai.android_sdk.data.result.auto_complete;

import com.google.gson.annotations.SerializedName;

public class AutoCompleteResult{

	@SerializedName("error")
	private Object error;

	@SerializedName("results")
	private Results results;

	public Object getError(){
		return error;
	}

	public Results getResults(){
		return results;
	}
}
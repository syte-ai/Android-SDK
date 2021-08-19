package com.syte.ai.android_sdk.data.result.text_search;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class TextSearchResult{

	@SerializedName("result")
	private Result result;

	@SerializedName("error")
	private String error;

	public Result getResult(){
		return result;
	}

	@Nullable
	public String getError(){
		return error;
	}

}
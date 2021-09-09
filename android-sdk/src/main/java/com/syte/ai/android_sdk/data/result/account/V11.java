package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class V11{

	@SerializedName("personalization")
	private Personalization personalization;

	@SerializedName("features")
	private List<Object> features;

	@SerializedName("similars")
	private Similars similars;

	@SerializedName("flags")
	private List<Object> flags;

	@SerializedName("default_currency")
	private String defaultCurrency;

	public Personalization getPersonalization(){
		return personalization;
	}

	public List<Object> getFeatures(){
		return features;
	}

	public Similars getSimilars(){
		return similars;
	}

	public List<Object> getFlags(){
		return flags;
	}

	public String getDefaultCurrency(){
		return defaultCurrency;
	}

	@Override
 	public String toString(){
		return 
			"V11{" + 
			"personalization = '" + personalization + '\'' + 
			",features = '" + features + '\'' + 
			",similars = '" + similars + '\'' + 
			",flags = '" + flags + '\'' + 
			",default_currency = '" + defaultCurrency + '\'' + 
			"}";
		}
}
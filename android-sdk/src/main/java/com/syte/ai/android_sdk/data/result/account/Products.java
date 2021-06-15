package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Products{

	@SerializedName("syteapp")
	private Syteapp syteapp;

	@SerializedName("personalisation")
	private Personalisation personalisation;

	public Syteapp getSyteapp(){
		return syteapp;
	}

	public Personalisation getPersonalisation(){
		return personalisation;
	}

	@Override
 	public String toString(){
		return 
			"Products{" + 
			"syteapp = '" + syteapp + '\'' + 
			",personalisation = '" + personalisation + '\'' + 
			"}";
		}
}
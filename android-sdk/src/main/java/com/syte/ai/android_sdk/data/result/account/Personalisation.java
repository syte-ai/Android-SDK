package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Personalisation{

	@SerializedName("features")
	private Features features;

	@SerializedName("modelTrainHourInterval")
	private int modelTrainHourInterval;

	public Features getFeatures(){
		return features;
	}

	public int getModelTrainHourInterval(){
		return modelTrainHourInterval;
	}

	@Override
 	public String toString(){
		return 
			"Personalisation{" + 
			"features = '" + features + '\'' + 
			",modelTrainHourInterval = '" + modelTrainHourInterval + '\'' + 
			"}";
		}
}
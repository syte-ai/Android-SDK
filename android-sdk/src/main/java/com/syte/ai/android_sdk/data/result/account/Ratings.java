package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Ratings{

	@SerializedName("minRatingToShow")
	private int minRatingToShow;

	@SerializedName("displayRatingCount")
	private boolean displayRatingCount;

	@SerializedName("displayRatings")
	private boolean displayRatings;

	public int getMinRatingToShow(){
		return minRatingToShow;
	}

	public boolean isDisplayRatingCount(){
		return displayRatingCount;
	}

	public boolean isDisplayRatings(){
		return displayRatings;
	}

	@Override
 	public String toString(){
		return 
			"Ratings{" + 
			"minRatingToShow = '" + minRatingToShow + '\'' + 
			",displayRatingCount = '" + displayRatingCount + '\'' + 
			",displayRatings = '" + displayRatings + '\'' + 
			"}";
		}
}
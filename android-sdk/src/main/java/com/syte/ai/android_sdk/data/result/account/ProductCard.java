package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class ProductCard{

	@SerializedName("ratings")
	private Ratings ratings;

	@SerializedName("addToCart")
	private AddToCart addToCart;

	public Ratings getRatings(){
		return ratings;
	}

	public AddToCart getAddToCart(){
		return addToCart;
	}

	@Override
 	public String toString(){
		return 
			"ProductCard{" + 
			"ratings = '" + ratings + '\'' + 
			",addToCart = '" + addToCart + '\'' + 
			"}";
		}
}
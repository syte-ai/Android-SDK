package com.syte.ai.android_sdk.data.result.auto_complete;

import com.google.gson.annotations.SerializedName;

public class SuggestedItem {

	@SerializedName("offer")
	private String offer;

	@SerializedName("floatPrice")
	private double floatPrice;

	@SerializedName("originalPrice")
	private String originalPrice;

	@SerializedName("price")
	private String price;

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("description")
	private String description;

	@SerializedName("floatOriginalPrice")
	private double floatOriginalPrice;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	public String getOffer(){
		return offer;
	}

	public double getFloatPrice(){
		return floatPrice;
	}

	public String getOriginalPrice(){
		return originalPrice;
	}

	public String getPrice(){
		return price;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public String getDescription(){
		return description;
	}

	public double getFloatOriginalPrice(){
		return floatOriginalPrice;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}
}
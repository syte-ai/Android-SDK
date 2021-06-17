package com.syte.ai.android_sdk.data.result.offers;

import androidx.annotation.Nullable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class Offer {

	@SerializedName("floatPrice")
	private double floatPrice;

	@SerializedName("originalPrice")
	private String originalPrice;

	@SerializedName("parent_sku")
	private String parentSku;

	@SerializedName("merchant")
	private String merchant;

	@SerializedName("description")
	private String description;

	@SerializedName("offer")
	private String offer;

	private JSONObject originalData = null;

	@SerializedName("price")
	private String price;

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("bbCategories")
	private List<String> bbCategories;

	@SerializedName("id")
	private String id;

	@SerializedName("floatOriginalPrice")
	private double floatOriginalPrice;

	@SerializedName("categories")
	private List<String> categories;

	@SerializedName("sku")
	private String sku;

	@SerializedName("brand")
	private String brand;

	public double getFloatPrice(){
		return floatPrice;
	}

	public String getOriginalPrice(){
		return originalPrice;
	}

	public String getParentSku(){
		return parentSku;
	}

	public String getMerchant(){
		return merchant;
	}

	public String getDescription(){
		return description;
	}

	public String getOffer(){
		return offer;
	}

	@Nullable
	public JSONObject getOriginalData(){
		return originalData;
	}

	public void setOriginalData(JSONObject originalData) {
		this.originalData = originalData;
	}

	public String getPrice(){
		return price;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public List<String> getBbCategories(){
		return bbCategories;
	}

	public String getId(){
		return id;
	}

	public double getFloatOriginalPrice(){
		return floatOriginalPrice;
	}

	public List<String> getCategories(){
		return categories;
	}

	public String getSku(){
		return sku;
	}

	public String getBrand(){
		return brand;
	}

	@Override
 	public String toString(){
		return 
			"AdsItem{" + 
			"floatPrice = '" + floatPrice + '\'' + 
			",originalPrice = '" + originalPrice + '\'' + 
			",parent_sku = '" + parentSku + '\'' + 
			",merchant = '" + merchant + '\'' + 
			",description = '" + description + '\'' + 
			",offer = '" + offer + '\'' + 
			",original_data = '" + originalData + '\'' +
			",price = '" + price + '\'' + 
			",imageUrl = '" + imageUrl + '\'' + 
			",bbCategories = '" + bbCategories + '\'' + 
			",id = '" + id + '\'' + 
			",floatOriginalPrice = '" + floatOriginalPrice + '\'' + 
			",categories = '" + categories + '\'' + 
			",sku = '" + sku + '\'' + 
			",brand = '" + brand + '\'' + 
			"}";
		}
}
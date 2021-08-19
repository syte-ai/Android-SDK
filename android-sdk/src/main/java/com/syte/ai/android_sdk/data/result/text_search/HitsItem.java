package com.syte.ai.android_sdk.data.result.text_search;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

public class HitsItem{

	@SerializedName("tagging")
	private Tagging tagging;

	@SerializedName("floatPrice")
	private double floatPrice;

	@SerializedName("originalPrice")
	private String originalPrice;

	@SerializedName("keywords_compound")
	private String keywordsCompound;

	@SerializedName("merchant")
	private String merchant;

	@SerializedName("description")
	private String description;

	@SerializedName("matched_queries")
	private String matchedQueries;

	@SerializedName("title")
	private String title;

	@SerializedName("bb_category")
	private String bbCategory;

	@SerializedName("tags")
	private List<String> tags;

	@SerializedName("offer")
	private String offer;

	private JSONObject originalData;

	@SerializedName("syte_category")
	private String syteCategory;

	@SerializedName("price")
	private String price;

	@SerializedName("imageUrl")
	private String imageUrl;

	@SerializedName("id")
	private String id;

	@SerializedName("categories")
	private List<String> categories;

	@SerializedName("floatOriginalPrice")
	private double floatOriginalPrice;

	@SerializedName("brand")
	private String brand;

	public Tagging getTagging(){
		return tagging;
	}

	public double getFloatPrice(){
		return floatPrice;
	}

	public String getOriginalPrice(){
		return originalPrice;
	}

	public String getKeywordsCompound(){
		return keywordsCompound;
	}

	public String getMerchant(){
		return merchant;
	}

	public String getDescription(){
		return description;
	}

	public String getMatchedQueries(){
		return matchedQueries;
	}

	public String getTitle(){
		return title;
	}

	public String getBbCategory(){
		return bbCategory;
	}

	public List<String> getTags(){
		return tags;
	}

	public String getOffer(){
		return offer;
	}

	public JSONObject getOriginalData(){
		return originalData;
	}

	public String getSyteCategory(){
		return syteCategory;
	}

	public String getPrice(){
		return price;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public String getId(){
		return id;
	}

	public List<String> getCategories(){
		return categories;
	}

	public double getFloatOriginalPrice(){
		return floatOriginalPrice;
	}

	public String getBrand(){
		return brand;
	}

	public void setOriginalData(JSONObject originalData) {
		this.originalData = originalData;
	}
}
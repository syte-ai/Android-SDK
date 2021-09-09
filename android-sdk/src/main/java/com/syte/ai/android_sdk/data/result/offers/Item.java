package com.syte.ai.android_sdk.data.result.offers;

import androidx.annotation.Nullable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * Represents Item entity
 */
public class Item {

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

	/**
	 * Getter for float price
	 * @return float price
	 */
	public double getFloatPrice(){
		return floatPrice;
	}

	/**
	 * Getter for original price
	 * @return original price
	 */
	public String getOriginalPrice(){
		return originalPrice;
	}

	/**
	 * Getter for parent SKU (product ID)
	 * @return parent SKU
	 */
	public String getParentSku(){
		return parentSku;
	}

	/**
	 * Getter for merchant
	 * @return merchant
	 */
	public String getMerchant(){
		return merchant;
	}

	/**
	 * Getter for description
	 * @return description
	 */
	public String getDescription(){
		return description;
	}

	/**
	 * Getter for item Url
	 * @return item Url
	 */
	public String getItemUrl(){
		return offer;
	}

	/**
	 * Getter for original data. This data can be unique for each account ID
	 * @return original data
	 */
	@Nullable
	public JSONObject getOriginalData(){
		return originalData;
	}

	/**
	 * Setter for original data. Is used internally.
	 * @param originalData original data
	 */
	public void setOriginalData(JSONObject originalData) {
		this.originalData = originalData;
	}

	/**
	 * Getter for price
	 * @return price
	 */
	public String getPrice(){
		return price;
	}

	/**
	 * Getter for image Url
	 * @return image Url
	 */
	public String getImageUrl(){
		return imageUrl;
	}

	/**
	 * Getter for BB categories
	 * @return BB categories
	 */
	public List<String> getBbCategories(){
		return bbCategories;
	}

	/**
	 * Getter for ID
	 * @return ID
	 */
	public String getId(){
		return id;
	}

	/**
	 * Getter for float original price
	 * @return float original price
	 */
	public double getFloatOriginalPrice(){
		return floatOriginalPrice;
	}

	/**
	 * Getter for categories
	 * @return categories
	 */
	public List<String> getCategories(){
		return categories;
	}

	/**
	 * Getter for SKU (product ID)
	 * @return SKU
	 */
	public String getSku(){
		return sku;
	}

	/**
	 * Getter for brand
	 * @return brand
	 */
	public String getBrand(){
		return brand;
	}

}
package com.syte.ai.android_sdk.data.result.text_search;

import com.google.gson.annotations.SerializedName;

public class OriginalData{

	@SerializedName("image_link")
	private String imageLink;

	@SerializedName("gender")
	private String gender;

	@SerializedName("saleprice")
	private String saleprice;

	@SerializedName("discount")
	private String discount;

	@SerializedName("additional_image_link")
	private String additionalImageLink;

	@SerializedName("size")
	private String size;

	@SerializedName("price")
	private String price;

	@SerializedName("availability ")
	private String availability;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("products_URL")
	private String productsURL;

	@SerializedName("currency ")
	private String currency;

	@SerializedName("brand")
	private String brand;

	@SerializedName("product_category")
	private String productCategory;

	public String getImageLink(){
		return imageLink;
	}

	public String getGender(){
		return gender;
	}

	public String getSaleprice(){
		return saleprice;
	}

	public String getDiscount(){
		return discount;
	}

	public String getAdditionalImageLink(){
		return additionalImageLink;
	}

	public String getSize(){
		return size;
	}

	public String getPrice(){
		return price;
	}

	public String getAvailability(){
		return availability;
	}

	public String getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public String getProductsURL(){
		return productsURL;
	}

	public String getCurrency(){
		return currency;
	}

	public String getBrand(){
		return brand;
	}

	public String getProductCategory(){
		return productCategory;
	}
}
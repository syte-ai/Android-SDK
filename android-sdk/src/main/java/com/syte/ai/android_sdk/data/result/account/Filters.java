package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Filters{

	@SerializedName("gender")
	private Gender gender;

	@SerializedName("size")
	private Size size;

	@SerializedName("price")
	private Price price;

	@SerializedName("custom")
	private List<Object> custom;

	@SerializedName("brand")
	private Brand brand;

	@SerializedName("colors")
	private Colors colors;

	@SerializedName("categoryFilters")
	private List<Object> categoryFilters;

	@SerializedName("allowedFilters")
	private List<String> allowedFilters;

	public Gender getGender(){
		return gender;
	}

	public Size getSize(){
		return size;
	}

	public Price getPrice(){
		return price;
	}

	public List<Object> getCustom(){
		return custom;
	}

	public Brand getBrand(){
		return brand;
	}

	public Colors getColors(){
		return colors;
	}

	public List<Object> getCategoryFilters(){
		return categoryFilters;
	}

	public List<String> getAllowedFilters(){
		return allowedFilters;
	}

	@Override
 	public String toString(){
		return 
			"Filters{" + 
			"gender = '" + gender + '\'' + 
			",size = '" + size + '\'' + 
			",price = '" + price + '\'' + 
			",custom = '" + custom + '\'' + 
			",brand = '" + brand + '\'' + 
			",colors = '" + colors + '\'' + 
			",categoryFilters = '" + categoryFilters + '\'' + 
			",allowedFilters = '" + allowedFilters + '\'' + 
			"}";
		}
}
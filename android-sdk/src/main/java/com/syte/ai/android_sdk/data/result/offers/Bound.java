package com.syte.ai.android_sdk.data.result.offers;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Bound {

	@SerializedName("offers")
	private String offersUrl;

	@SerializedName("gender")
	private String gender;

	@SerializedName("catalog")
	private String catalog;

	@SerializedName("center")
	private List<Double> center;

	@SerializedName("label")
	private String label;

	@SerializedName("b0")
	private List<Double> b0;

	@SerializedName("b1")
	private List<Double> b1;

	public String getOffersUrl(){
		return offersUrl;
	}

	public String getGender(){
		return gender;
	}

	public String getCatalog(){
		return catalog;
	}

	public List<Double> getCenter(){
		return center;
	}

	public String getLabel(){
		return label;
	}

	public List<Double> getB0(){
		return b0;
	}

	public List<Double> getB1(){
		return b1;
	}
}
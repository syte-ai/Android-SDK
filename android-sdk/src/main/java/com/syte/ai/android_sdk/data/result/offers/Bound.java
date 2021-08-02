package com.syte.ai.android_sdk.data.result.offers;

import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Represents Bound entity.
 */
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

	/**
	 * Getter for Offers Url
	 * @return Offers Url
	 */
	public String getOffersUrl(){
		return offersUrl;
	}

	/**
	 * Getter for gender
	 * @return gender
	 */
	public String getGender(){
		return gender;
	}

	/**
	 * Getter for catalog
	 * @return catalog
	 */
	public String getCatalog(){
		return catalog;
	}

	/**
	 * Getter for bound center
	 * @return bound center
	 */
	public List<Double> getCenter(){
		return center;
	}

	/**
	 * Getter for label
	 * @return label
	 */
	public String getLabel(){
		return label;
	}

	/**
	 * Getter for the top left point coordinates
	 * @return top left point coordinates
	 */
	public List<Double> getB0(){
		return b0;
	}

	/**
	 * Getter for the bottom right point coordinates
	 * @return bottom right point coordinates
	 */
	public List<Double> getB1(){
		return b1;
	}

}
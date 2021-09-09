package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Feeds{

	@SerializedName("general")
	private List<Object> general;

	@SerializedName("related_looks")
	private List<Object> relatedLooks;

	@SerializedName("fashion")
	private List<String> fashion;

	@SerializedName("home")
	private List<Object> home;

	public List<Object> getGeneral(){
		return general;
	}

	public List<Object> getRelatedLooks(){
		return relatedLooks;
	}

	public List<String> getFashion(){
		return fashion;
	}

	public List<Object> getHome(){
		return home;
	}

	@Override
 	public String toString(){
		return 
			"Feeds{" + 
			"general = '" + general + '\'' + 
			",related_looks = '" + relatedLooks + '\'' + 
			",fashion = '" + fashion + '\'' + 
			",home = '" + home + '\'' + 
			"}";
		}
}
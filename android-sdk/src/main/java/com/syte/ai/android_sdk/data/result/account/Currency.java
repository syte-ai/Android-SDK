package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Currency{

	@SerializedName("precision")
	private int precision;

	@SerializedName("format")
	private String format;

	@SerializedName("decimal")
	private String decimal;

	@SerializedName("thousands")
	private String thousands;

	public int getPrecision(){
		return precision;
	}

	public String getFormat(){
		return format;
	}

	public String getDecimal(){
		return decimal;
	}

	public String getThousands(){
		return thousands;
	}

	@Override
 	public String toString(){
		return 
			"Currency{" + 
			"precision = '" + precision + '\'' + 
			",format = '" + format + '\'' + 
			",decimal = '" + decimal + '\'' + 
			",thousands = '" + thousands + '\'' + 
			"}";
		}
}
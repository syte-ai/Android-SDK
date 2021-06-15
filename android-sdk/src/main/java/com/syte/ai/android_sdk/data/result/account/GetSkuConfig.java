package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetSkuConfig{

	@SerializedName("skuProvidersOrder")
	private List<String> skuProvidersOrder;

	@SerializedName("shouldReportMissingSkus")
	private boolean shouldReportMissingSkus;

	@SerializedName("functionConfig")
	private Object functionConfig;

	public List<String> getSkuProvidersOrder(){
		return skuProvidersOrder;
	}

	public boolean isShouldReportMissingSkus(){
		return shouldReportMissingSkus;
	}

	public Object getFunctionConfig(){
		return functionConfig;
	}

	@Override
 	public String toString(){
		return 
			"GetSkuConfig{" + 
			"skuProvidersOrder = '" + skuProvidersOrder + '\'' + 
			",shouldReportMissingSkus = '" + shouldReportMissingSkus + '\'' + 
			",functionConfig = '" + functionConfig + '\'' + 
			"}";
		}
}
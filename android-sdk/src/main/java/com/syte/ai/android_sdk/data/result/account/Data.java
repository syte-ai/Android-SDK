package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Data{

	@SerializedName("checkFeedHealth")
	private boolean checkFeedHealth;

	@SerializedName("isProduction")
	private boolean isProduction;

	@SerializedName("crossProductFeatures")
	private CrossProductFeatures crossProductFeatures;

	@SerializedName("version")
	private int version;

	@SerializedName("enabled")
	private boolean enabled;

	@SerializedName("logoUrl")
	private String logoUrl;

	@SerializedName("products")
	private Products products;

	@SerializedName("accountId")
	private int accountId;

	@SerializedName("feedsDefaults")
	private FeedsDefaults feedsDefaults;

	@SerializedName("domain")
	private String domain;

	@SerializedName("feeds")
	private Feeds feeds;

	@SerializedName("api")
	private Api api;

	@SerializedName("ecommercePixel")
	private boolean ecommercePixel;

	public boolean isCheckFeedHealth(){
		return checkFeedHealth;
	}

	public boolean isIsProduction(){
		return isProduction;
	}

	public CrossProductFeatures getCrossProductFeatures(){
		return crossProductFeatures;
	}

	public int getVersion(){
		return version;
	}

	public boolean isEnabled(){
		return enabled;
	}

	public String getLogoUrl(){
		return logoUrl;
	}

	public Products getProducts(){
		return products;
	}

	public int getAccountId(){
		return accountId;
	}

	public FeedsDefaults getFeedsDefaults(){
		return feedsDefaults;
	}

	public String getDomain(){
		return domain;
	}

	public Feeds getFeeds(){
		return feeds;
	}

	public Api getApi(){
		return api;
	}

	public boolean isEcommercePixel(){
		return ecommercePixel;
	}

	@Override
 	public String toString(){
		return 
			"Data{" + 
			"checkFeedHealth = '" + checkFeedHealth + '\'' + 
			",isProduction = '" + isProduction + '\'' + 
			",crossProductFeatures = '" + crossProductFeatures + '\'' + 
			",version = '" + version + '\'' + 
			",enabled = '" + enabled + '\'' + 
			",logoUrl = '" + logoUrl + '\'' + 
			",products = '" + products + '\'' + 
			",accountId = '" + accountId + '\'' + 
			",feedsDefaults = '" + feedsDefaults + '\'' + 
			",domain = '" + domain + '\'' + 
			",feeds = '" + feeds + '\'' + 
			",api = '" + api + '\'' + 
			",ecommercePixel = '" + ecommercePixel + '\'' + 
			"}";
		}
}
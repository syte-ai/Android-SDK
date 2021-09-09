package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class DiscoveryButton{

	@SerializedName("css")
	private String css;

	@SerializedName("active")
	private boolean active;

	@SerializedName("enableDesktopSort")
	private boolean enableDesktopSort;

	@SerializedName("cropper")
	private Cropper cropper;

	@SerializedName("enableMobileFilters")
	private boolean enableMobileFilters;

	@SerializedName("overrideImageUrl")
	private OverrideImageUrl overrideImageUrl;

	@SerializedName("enableDesktopFilters")
	private boolean enableDesktopFilters;

	@SerializedName("icon2xUrl")
	private String icon2xUrl;

	@SerializedName("showAdditionalPersonalisedResults")
	private boolean showAdditionalPersonalisedResults;

	@SerializedName("elementSelector")
	private String elementSelector;

	@SerializedName("enableMobileSort")
	private boolean enableMobileSort;

	@SerializedName("selector")
	private String selector;

	@SerializedName("iconUrl")
	private String iconUrl;

	@SerializedName("showPersonalResults")
	private boolean showPersonalResults;

	@SerializedName("selector2x")
	private String selector2x;

	public String getCss(){
		return css;
	}

	public boolean isActive(){
		return active;
	}

	public boolean isEnableDesktopSort(){
		return enableDesktopSort;
	}

	public Cropper getCropper(){
		return cropper;
	}

	public boolean isEnableMobileFilters(){
		return enableMobileFilters;
	}

	public OverrideImageUrl getOverrideImageUrl(){
		return overrideImageUrl;
	}

	public boolean isEnableDesktopFilters(){
		return enableDesktopFilters;
	}

	public String getIcon2xUrl(){
		return icon2xUrl;
	}

	public boolean isShowAdditionalPersonalisedResults(){
		return showAdditionalPersonalisedResults;
	}

	public String getElementSelector(){
		return elementSelector;
	}

	public boolean isEnableMobileSort(){
		return enableMobileSort;
	}

	public String getSelector(){
		return selector;
	}

	public String getIconUrl(){
		return iconUrl;
	}

	public boolean isShowPersonalResults(){
		return showPersonalResults;
	}

	public String getSelector2x(){
		return selector2x;
	}

	@Override
 	public String toString(){
		return 
			"DiscoveryButton{" + 
			"css = '" + css + '\'' + 
			",active = '" + active + '\'' + 
			",enableDesktopSort = '" + enableDesktopSort + '\'' + 
			",cropper = '" + cropper + '\'' + 
			",enableMobileFilters = '" + enableMobileFilters + '\'' + 
			",overrideImageUrl = '" + overrideImageUrl + '\'' + 
			",enableDesktopFilters = '" + enableDesktopFilters + '\'' + 
			",icon2xUrl = '" + icon2xUrl + '\'' + 
			",showAdditionalPersonalisedResults = '" + showAdditionalPersonalisedResults + '\'' + 
			",elementSelector = '" + elementSelector + '\'' + 
			",enableMobileSort = '" + enableMobileSort + '\'' + 
			",selector = '" + selector + '\'' + 
			",iconUrl = '" + iconUrl + '\'' + 
			",showPersonalResults = '" + showPersonalResults + '\'' + 
			",selector2x = '" + selector2x + '\'' + 
			"}";
		}
}
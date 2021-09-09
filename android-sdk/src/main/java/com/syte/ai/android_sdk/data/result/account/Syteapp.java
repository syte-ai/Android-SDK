package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Syteapp{

	@SerializedName("features")
	private Features features;

	@SerializedName("preloadFonts")
	private boolean preloadFonts;

	@SerializedName("showBanner")
	private boolean showBanner;

	@SerializedName("requireApproval")
	private boolean requireApproval;

	@SerializedName("customBrand")
	private String customBrand;

	@SerializedName("catalog")
	private String catalog;

	@SerializedName("theme")
	private Theme theme;

	@SerializedName("originalUrl")
	private String originalUrl;

	@SerializedName("shouldReportPageViews")
	private boolean shouldReportPageViews;

	@SerializedName("url")
	private String url;

	@SerializedName("enabled")
	private boolean enabled;

	public Features getFeatures(){
		return features;
	}

	public boolean isPreloadFonts(){
		return preloadFonts;
	}

	public boolean isShowBanner(){
		return showBanner;
	}

	public boolean isRequireApproval(){
		return requireApproval;
	}

	public String getCustomBrand(){
		return customBrand;
	}

	public String getCatalog(){
		return catalog;
	}

	public Theme getTheme(){
		return theme;
	}

	public String getOriginalUrl(){
		return originalUrl;
	}

	public boolean isShouldReportPageViews(){
		return shouldReportPageViews;
	}

	public String getUrl(){
		return url;
	}

	public boolean isEnabled(){
		return enabled;
	}

	@Override
 	public String toString(){
		return 
			"Syteapp{" + 
			"features = '" + features + '\'' + 
			",preloadFonts = '" + preloadFonts + '\'' + 
			",showBanner = '" + showBanner + '\'' + 
			",requireApproval = '" + requireApproval + '\'' + 
			",customBrand = '" + customBrand + '\'' + 
			",catalog = '" + catalog + '\'' + 
			",theme = '" + theme + '\'' + 
			",originalUrl = '" + originalUrl + '\'' + 
			",shouldReportPageViews = '" + shouldReportPageViews + '\'' + 
			",url = '" + url + '\'' + 
			",enabled = '" + enabled + '\'' + 
			"}";
		}
}
package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class Cropper{

	@SerializedName("forceGeneral")
	private boolean forceGeneral;

	@SerializedName("feed")
	private String feed;

	@SerializedName("force_cats")
	private String forceCats;

	@SerializedName("catalog")
	private String catalog;

	@SerializedName("showCropBtn")
	private boolean showCropBtn;

	@SerializedName("disableManualCrop")
	private boolean disableManualCrop;

	@SerializedName("enabled")
	private boolean enabled;

	public boolean isForceGeneral(){
		return forceGeneral;
	}

	public String getFeed(){
		return feed;
	}

	public String getForceCats(){
		return forceCats;
	}

	public String getCatalog(){
		return catalog;
	}

	public boolean isShowCropBtn(){
		return showCropBtn;
	}

	public boolean isDisableManualCrop(){
		return disableManualCrop;
	}

	public boolean isEnabled(){
		return enabled;
	}

	@Override
 	public String toString(){
		return 
			"Cropper{" + 
			"forceGeneral = '" + forceGeneral + '\'' + 
			",feed = '" + feed + '\'' + 
			",force_cats = '" + forceCats + '\'' + 
			",catalog = '" + catalog + '\'' + 
			",showCropBtn = '" + showCropBtn + '\'' + 
			",disableManualCrop = '" + disableManualCrop + '\'' + 
			",enabled = '" + enabled + '\'' + 
			"}";
		}
}
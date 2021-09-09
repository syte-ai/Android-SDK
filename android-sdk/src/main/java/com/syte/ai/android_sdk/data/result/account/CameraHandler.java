package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class CameraHandler{

	@SerializedName("enableMobileFilters")
	private boolean enableMobileFilters;

	@SerializedName("enablePersonalisationTourImages")
	private boolean enablePersonalisationTourImages;

	@SerializedName("enableDesktopFilters")
	private boolean enableDesktopFilters;

	@SerializedName("inMaintenance")
	private boolean inMaintenance;

	@SerializedName("mobileTourScreen")
	private MobileTourScreen mobileTourScreen;

	@SerializedName("active")
	private boolean active;

	@SerializedName("enableMobileSort")
	private boolean enableMobileSort;

	@SerializedName("enableDesktopSort")
	private boolean enableDesktopSort;

	@SerializedName("photoReductionSize")
	private String photoReductionSize;

	public boolean isEnableMobileFilters(){
		return enableMobileFilters;
	}

	public boolean isEnablePersonalisationTourImages(){
		return enablePersonalisationTourImages;
	}

	public boolean isEnableDesktopFilters(){
		return enableDesktopFilters;
	}

	public boolean isInMaintenance(){
		return inMaintenance;
	}

	public MobileTourScreen getMobileTourScreen(){
		return mobileTourScreen;
	}

	public boolean isActive(){
		return active;
	}

	public boolean isEnableMobileSort(){
		return enableMobileSort;
	}

	public boolean isEnableDesktopSort(){
		return enableDesktopSort;
	}

	public String getPhotoReductionSize(){
		return photoReductionSize;
	}

	@Override
 	public String toString(){
		return 
			"CameraHandler{" + 
			"enableMobileFilters = '" + enableMobileFilters + '\'' + 
			",enablePersonalisationTourImages = '" + enablePersonalisationTourImages + '\'' + 
			",enableDesktopFilters = '" + enableDesktopFilters + '\'' + 
			",inMaintenance = '" + inMaintenance + '\'' + 
			",mobileTourScreen = '" + mobileTourScreen + '\'' + 
			",active = '" + active + '\'' + 
			",enableMobileSort = '" + enableMobileSort + '\'' + 
			",enableDesktopSort = '" + enableDesktopSort + '\'' + 
			",photoReductionSize = '" + photoReductionSize + '\'' + 
			"}";
		}
}
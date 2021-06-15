package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class TopSearch{

	@SerializedName("itemsPerPage")
	private int itemsPerPage;

	@SerializedName("active")
	private boolean active;

	@SerializedName("selector")
	private String selector;

	@SerializedName("showGalleryCTA")
	private boolean showGalleryCTA;

	@SerializedName("showCameraCTA")
	private boolean showCameraCTA;

	@SerializedName("showSearchBar")
	private boolean showSearchBar;

	@SerializedName("customCSS")
	private String customCSS;

	public int getItemsPerPage(){
		return itemsPerPage;
	}

	public boolean isActive(){
		return active;
	}

	public String getSelector(){
		return selector;
	}

	public boolean isShowGalleryCTA(){
		return showGalleryCTA;
	}

	public boolean isShowCameraCTA(){
		return showCameraCTA;
	}

	public boolean isShowSearchBar(){
		return showSearchBar;
	}

	public String getCustomCSS(){
		return customCSS;
	}

	@Override
 	public String toString(){
		return 
			"TopSearch{" + 
			"itemsPerPage = '" + itemsPerPage + '\'' + 
			",active = '" + active + '\'' + 
			",selector = '" + selector + '\'' + 
			",showGalleryCTA = '" + showGalleryCTA + '\'' + 
			",showCameraCTA = '" + showCameraCTA + '\'' + 
			",showSearchBar = '" + showSearchBar + '\'' + 
			",customCSS = '" + customCSS + '\'' + 
			"}";
		}
}
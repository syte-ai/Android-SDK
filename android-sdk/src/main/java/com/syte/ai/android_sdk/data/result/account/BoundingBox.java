package com.syte.ai.android_sdk.data.result.account;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BoundingBox{

	@SerializedName("offerNavBehaviourDesktop")
	private String offerNavBehaviourDesktop;

	@SerializedName("filtersMobileLayoutV2")
	private boolean filtersMobileLayoutV2;

	@SerializedName("active")
	private boolean active;

	@SerializedName("offerNavBehaviourMobile")
	private String offerNavBehaviourMobile;

	@SerializedName("inspoGalleryImagesSkus")
	private List<Object> inspoGalleryImagesSkus;

	@SerializedName("inspoGalleryImages")
	private List<String> inspoGalleryImages;

	@SerializedName("filters")
	private Filters filters;

	@SerializedName("desktopTheme")
	private String desktopTheme;

	@SerializedName("imageSelector")
	private String imageSelector;

	@SerializedName("cropper")
	private Cropper cropper;

	@SerializedName("numOfAds")
	private int numOfAds;

	@SerializedName("decoratorType")
	private String decoratorType;

	@SerializedName("requireApproval")
	private boolean requireApproval;

	@SerializedName("showQuickView")
	private boolean showQuickView;

	@SerializedName("theme")
	private String theme;

	public String getOfferNavBehaviourDesktop(){
		return offerNavBehaviourDesktop;
	}

	public boolean isFiltersMobileLayoutV2(){
		return filtersMobileLayoutV2;
	}

	public boolean isActive(){
		return active;
	}

	public String getOfferNavBehaviourMobile(){
		return offerNavBehaviourMobile;
	}

	public List<Object> getInspoGalleryImagesSkus(){
		return inspoGalleryImagesSkus;
	}

	public List<String> getInspoGalleryImages(){
		return inspoGalleryImages;
	}

	public Filters getFilters(){
		return filters;
	}

	public String getDesktopTheme(){
		return desktopTheme;
	}

	public String getImageSelector(){
		return imageSelector;
	}

	public Cropper getCropper(){
		return cropper;
	}

	public int getNumOfAds(){
		return numOfAds;
	}

	public String getDecoratorType(){
		return decoratorType;
	}

	public boolean isRequireApproval(){
		return requireApproval;
	}

	public boolean isShowQuickView(){
		return showQuickView;
	}

	public String getTheme(){
		return theme;
	}

	@Override
 	public String toString(){
		return 
			"BoundingBox{" + 
			"offerNavBehaviourDesktop = '" + offerNavBehaviourDesktop + '\'' + 
			",filtersMobileLayoutV2 = '" + filtersMobileLayoutV2 + '\'' + 
			",active = '" + active + '\'' + 
			",offerNavBehaviourMobile = '" + offerNavBehaviourMobile + '\'' + 
			",inspoGalleryImagesSkus = '" + inspoGalleryImagesSkus + '\'' + 
			",inspoGalleryImages = '" + inspoGalleryImages + '\'' + 
			",filters = '" + filters + '\'' + 
			",desktopTheme = '" + desktopTheme + '\'' + 
			",imageSelector = '" + imageSelector + '\'' + 
			",cropper = '" + cropper + '\'' + 
			",numOfAds = '" + numOfAds + '\'' + 
			",decoratorType = '" + decoratorType + '\'' + 
			",requireApproval = '" + requireApproval + '\'' + 
			",showQuickView = '" + showQuickView + '\'' + 
			",theme = '" + theme + '\'' + 
			"}";
		}
}
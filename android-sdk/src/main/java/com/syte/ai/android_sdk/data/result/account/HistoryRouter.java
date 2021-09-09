package com.syte.ai.android_sdk.data.result.account;

import com.google.gson.annotations.SerializedName;

public class HistoryRouter{

	@SerializedName("active")
	private boolean active;

	@SerializedName("openModalOnPageLoad")
	private boolean openModalOnPageLoad;

	public boolean isActive(){
		return active;
	}

	public boolean isOpenModalOnPageLoad(){
		return openModalOnPageLoad;
	}

	@Override
 	public String toString(){
		return 
			"HistoryRouter{" + 
			"active = '" + active + '\'' + 
			",openModalOnPageLoad = '" + openModalOnPageLoad + '\'' + 
			"}";
		}
}
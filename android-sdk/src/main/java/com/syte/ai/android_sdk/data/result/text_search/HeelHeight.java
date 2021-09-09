package com.syte.ai.android_sdk.data.result.text_search;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HeelHeight{

	@SerializedName("doc_count_error_upper_bound")
	private int docCountErrorUpperBound;

	@SerializedName("sum_other_doc_count")
	private int sumOtherDocCount;

	@SerializedName("buckets")
	private List<BucketsItem> buckets;

	@SerializedName("display_name")
	private String displayName;

	@SerializedName("order")
	private int order;

	public int getDocCountErrorUpperBound(){
		return docCountErrorUpperBound;
	}

	public int getSumOtherDocCount(){
		return sumOtherDocCount;
	}

	public List<BucketsItem> getBuckets(){
		return buckets;
	}

	public String getDisplayName(){
		return displayName;
	}

	public int getOrder(){
		return order;
	}
}
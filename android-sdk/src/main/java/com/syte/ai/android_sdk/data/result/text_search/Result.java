package com.syte.ai.android_sdk.data.result.text_search;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("hits")
	private List<HitsItem> hits;

	@SerializedName("total")
	private int total;

	@SerializedName("recognizedTagging")
	private RecognizedTagging recognizedTagging;

	@SerializedName("aggregations")
	private Aggregations aggregations;

	@SerializedName("exactCount")
	private int exactCount;

	public List<HitsItem> getHits(){
		return hits;
	}

	public int getTotal(){
		return total;
	}

	public RecognizedTagging getRecognizedTagging(){
		return recognizedTagging;
	}

	public Aggregations getAggregations(){
		return aggregations;
	}

	public int getExactCount(){
		return exactCount;
	}

}
package com.syte.ai.android_sdk.data.result.offers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class BoundsResult {

	@SerializedName("bounds")
	private List<Bound> bounds = new ArrayList<>();

	private OffersResult firstBoundOffersResult = null;

	public List<Bound> getBounds() {
		return bounds;
	}

	public void setFirstBoundOffersResult(OffersResult firstBoundOffersResult) {
		this.firstBoundOffersResult = firstBoundOffersResult;
	}

	@Nullable
	public OffersResult getFirstBoundOffersResult() {
		return firstBoundOffersResult;
	}

	@NonNull
	@NotNull
	@Override
	public String toString() {
		return bounds.toString() + "\nFirst bound offers result:" + firstBoundOffersResult;
	}

}
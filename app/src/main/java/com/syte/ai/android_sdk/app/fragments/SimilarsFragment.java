package com.syte.ai.android_sdk.app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.syte.ai.android_sdk.ProductRecommendationClient;
import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.app.common.BaseFragment;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;

import org.jetbrains.annotations.NotNull;


public class SimilarsFragment extends BaseFragment implements View.OnClickListener {

    private Button mGetSimilarsSync;
    private EditText mImageUrlEditText;
    private EditText mSKUEditText;
    private EditText mLimitET;
    private EditText mUrlRefererET;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_similars, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetSimilarsSync = view.findViewById(R.id.similars_btn);
        mImageUrlEditText = view.findViewById(R.id.image_url_et);
        mSKUEditText = view.findViewById(R.id.sku_et);
        mLimitET = view.findViewById(R.id.limit);
        mUrlRefererET = view.findViewById(R.id.url_referer);

        initViews();
    }

    private void initViews() {
        mGetSimilarsSync.setOnClickListener(this);
        mImageUrlEditText.setText(R.string.default_image_url);
        mSKUEditText.setText(R.string.default_sku);
        mLimitET.setText(R.string.default_limit);
        mUrlRefererET.setText(R.string.default_url_referer);
    }

    private boolean validateInputs() {
        try {
            Integer.parseInt(mLimitET.getText().toString());
        } catch (Exception e) {
            return false;
        }
        return !mUrlRefererET.getText().toString().isEmpty()
                && (!mImageUrlEditText.getText().toString().isEmpty()
                || !mSKUEditText.getText().toString().isEmpty());
    }

    @Override
    public void onClick(View v) {
        SimilarProductsRequestData similarProductsRequestData =
                new SimilarProductsRequestData(
                        mSKUEditText.getText().toString(),
                        mImageUrlEditText.getText().toString()
                );
        if (!mSyteManager.getViewedProducts().isEmpty()) {
            similarProductsRequestData.setPersonalizedRanking(true);
        }
        if (!validateInputs()) {
            showToast("Wrong input");
            return;
        }
        similarProductsRequestData.setLimit(Integer.parseInt(mLimitET.getText().toString()));
        similarProductsRequestData.setSyteUrlReferer(mUrlRefererET.getText().toString());
        switch (v.getId()) {
            case R.id.similars_btn:
                mSyteManager.getSimilars(
                        similarProductsRequestData,
                        syteResult -> {
                            if (syteResult.isSuccessful) {
                                mSyteManager.setLastRetrievedItemsList(
                                        syteResult.data.getItems()
                                );
                                mNavigator.offersFragment();
                            }
                        }
                );
                break;
        }
    }
}
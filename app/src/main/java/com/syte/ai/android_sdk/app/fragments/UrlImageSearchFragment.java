package com.syte.ai.android_sdk.app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.app.common.BaseFragment;
import com.syte.ai.android_sdk.data.UrlImageSearch;
import com.syte.ai.android_sdk.enums.SyteProductType;

import org.jetbrains.annotations.NotNull;


public class UrlImageSearchFragment extends BaseFragment implements View.OnClickListener {

    private Button mGetBoundsSyncButton;
    private CheckBox mFetchOffersForTheFirstBoundCheckBox;
    private EditText mImageUrlEditText;
    private EditText mSKUEditText;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_url_image_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetBoundsSyncButton = view.findViewById(R.id.bounds_btn);
        mFetchOffersForTheFirstBoundCheckBox = view.findViewById(R.id.fetch_offers_for_the_first_bound);
        mImageUrlEditText = view.findViewById(R.id.image_url_et);
        mSKUEditText = view.findViewById(R.id.sku_et);

        initViews();
    }

    private void initViews() {
        mGetBoundsSyncButton.setOnClickListener(this);
        mFetchOffersForTheFirstBoundCheckBox.setChecked(true);
        mImageUrlEditText.setText(R.string.default_image_url);
        mSKUEditText.setText(R.string.default_sku);
    }

    private boolean validateInputs() {
        return (!mImageUrlEditText.getText().toString().isEmpty()
                || !mSKUEditText.getText().toString().isEmpty());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bounds_btn:
                if (!validateInputs()) {
                    showToast("Wrong input");
                    return;
                }
                UrlImageSearch imageSearchRequestData = new UrlImageSearch(
                        mImageUrlEditText.getText().toString(),
                        SyteProductType.DISCOVERY_BUTTON
                );
                imageSearchRequestData.setRetrieveItemsForTheFirstBound(
                        mFetchOffersForTheFirstBoundCheckBox.isChecked()
                );
                imageSearchRequestData.setSku(
                        mSKUEditText.getText().toString()
                );
                if (!mSyteManager.getViewedProducts().isEmpty()) {
                    imageSearchRequestData.setPersonalizedRanking(true);
                }
                mSyteManager.urlImageSearch(
                        imageSearchRequestData,
                        syteResult -> {
                            if (syteResult.isSuccessful) {
                                mSyteManager.setLastRetrievedBoundsList(
                                        syteResult.data.getBounds()
                                );
                                mNavigator.boundsFragment();
                            }
                        }
                );
                break;
        }
    }
}
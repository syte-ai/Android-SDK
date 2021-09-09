package com.syte.ai.android_sdk.app.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.app.common.BaseFragment;
import com.syte.ai.android_sdk.data.ImageSearch;

import org.jetbrains.annotations.NotNull;

import static android.app.Activity.RESULT_OK;


public class WildImageSearchFragment extends BaseFragment implements View.OnClickListener {

    private static final int RESULT_LOAD_IMG = 20002;
    private Button mGetBoundsSyncButton;
    private Button mLoadImageButton;
    private CheckBox mFetchOffersForTheFirstBoundCheckBox;
    private Uri mSelectedImageUri = null;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wild_image_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetBoundsSyncButton = view.findViewById(R.id.bounds_btn);
        mLoadImageButton = view.findViewById(R.id.load_image_btn);
        mFetchOffersForTheFirstBoundCheckBox = view.findViewById(R.id.fetch_offers_for_the_first_bound);

        initViews();
    }

    private void initViews() {
        mGetBoundsSyncButton.setOnClickListener(this);
        mLoadImageButton.setOnClickListener(this);
        mFetchOffersForTheFirstBoundCheckBox.setChecked(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            mSelectedImageUri = data.getData();
        }
    }

    @Override
    public void onClick(View v) {
        ImageSearch requestData = new ImageSearch(mSelectedImageUri);
        requestData.setRetrieveItemsForTheFirstBound(mFetchOffersForTheFirstBoundCheckBox.isChecked());
        if (!mSyteManager.getViewedProducts().isEmpty()) {
            requestData.setPersonalizedRanking(true);
        }
        switch (v.getId()) {
            case R.id.load_image_btn:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                break;
            case R.id.bounds_btn:
                mSyteManager.wildImageSearch(
                        requestData,
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
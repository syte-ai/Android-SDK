package com.syte.ai.android_sdk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import static android.app.Activity.RESULT_OK;


public class WildImageSearchFragment extends BaseFragment implements View.OnClickListener {

    private static final int RESULT_LOAD_IMG = 20002;
    private Button mGetBoundsSyncButton;
    private Button mGetBoundsAsyncButton;
    private Button mLoadImageButton;
    private CheckBox mFetchOffersForTheFirstBoundCheckBox;
    private Uri mSelectedImageUri = null;

    private UrlImageSearchManager mImageSearchManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wild_image_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetBoundsSyncButton = view.findViewById(R.id.bounds_sync_btn);
        mGetBoundsAsyncButton = view.findViewById(R.id.bounds_async_btn);
        mLoadImageButton = view.findViewById(R.id.load_image_btn);
        mFetchOffersForTheFirstBoundCheckBox = view.findViewById(R.id.fetch_offers_for_the_first_bound);

        mImageSearchManager = SDKApplication.getInstance().getUrlImageSearchManager();
        initViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        mImageSearchManager.subscribe(this);
        try {
            mImageSearchManager.initSyte();
        } catch (SyteInitializationException syteInitializationException) {
            showToast(null);
        }
    }

    public void onBoundsRetrieved(BoundsResult result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Navigator(getActivity().getSupportFragmentManager()).boundsFragment();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        mImageSearchManager.unsubscribe();
    }

    private void initViews() {
        mGetBoundsSyncButton.setOnClickListener(this);
        mGetBoundsAsyncButton.setOnClickListener(this);
        mLoadImageButton.setOnClickListener(this);
        mFetchOffersForTheFirstBoundCheckBox.setChecked(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK) {
            mSelectedImageUri = data.getData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.load_image_btn:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
                break;
            case R.id.bounds_sync_btn:
                mImageSearchManager.getBoundsSyncWild(
                        mSelectedImageUri,
                        mFetchOffersForTheFirstBoundCheckBox.isChecked()
                );
                break;
            case R.id.bounds_async_btn:
                mImageSearchManager.getBoundsWildAsync(
                        mSelectedImageUri,
                        mFetchOffersForTheFirstBoundCheckBox.isChecked()
                );
                break;
        }
    }
}
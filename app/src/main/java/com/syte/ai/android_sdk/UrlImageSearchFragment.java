package com.syte.ai.android_sdk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;


public class UrlImageSearchFragment extends BaseFragment implements View.OnClickListener {

    private Button mGetBoundsSyncButton;
    private Button mGetBoundsAsyncButton;
    private CheckBox mFetchOffersForTheFirstBoundCheckBox;
    private EditText mImageUrlEditText;
    private EditText mSKUEditText;

    private UrlImageSearchManager mUrlImageSearchManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_url_image_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetBoundsSyncButton = view.findViewById(R.id.bounds_sync_btn);
        mGetBoundsAsyncButton = view.findViewById(R.id.bounds_async_btn);
        mFetchOffersForTheFirstBoundCheckBox = view.findViewById(R.id.fetch_offers_for_the_first_bound);
        mImageUrlEditText = view.findViewById(R.id.image_url_et);
        mSKUEditText = view.findViewById(R.id.sku_et);

        mUrlImageSearchManager = SDKApplication.getInstance().getUrlImageSearchManager();
        initViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        mUrlImageSearchManager.subscribe(this);
        try {
            mUrlImageSearchManager.initSyte();
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
        mUrlImageSearchManager.unsubscribe();
    }

    private void initViews() {
        mGetBoundsSyncButton.setOnClickListener(this);
        mGetBoundsAsyncButton.setOnClickListener(this);
        mFetchOffersForTheFirstBoundCheckBox.setChecked(true);
        mImageUrlEditText.setText("https://cdn-images.farfetch-contents.com/13/70/55/96/13705596_18130188_1000.jpg");
        mSKUEditText.setText("13705596");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bounds_sync_btn:
                mUrlImageSearchManager.getBoundsSync(
                        mImageUrlEditText.getText().toString(),
                        mSKUEditText.getText().toString(),
                        mFetchOffersForTheFirstBoundCheckBox.isChecked()
                );
                break;
            case R.id.bounds_async_btn:
                mUrlImageSearchManager.getBoundsAsync(
                        mImageUrlEditText.getText().toString(),
                        mSKUEditText.getText().toString(),
                        mFetchOffersForTheFirstBoundCheckBox.isChecked()
                );
                break;
        }
    }
}
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

import com.syte.ai.android_sdk.data.CropCoordinates;
import com.syte.ai.android_sdk.data.result.offers.BoundsResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;


public class UrlImageSearchFragment extends BaseFragment implements View.OnClickListener {

    private Button mGetBoundsSyncButton;
    private Button mGetBoundsAsyncButton;
    private CheckBox mFetchOffersForTheFirstBoundCheckBox;
    private EditText mImageUrlEditText;
    private EditText mSKUEditText;
    private EditText mX1;
    private EditText mY1;
    private EditText mX2;
    private EditText mY2;
    private CheckBox mEnableCropCB;

    private SyteManager mSyteManager;

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
        mX1 = view.findViewById(R.id.x1);
        mY1 = view.findViewById(R.id.y1);
        mX2 = view.findViewById(R.id.x2);
        mY2 = view.findViewById(R.id.y2);
        mEnableCropCB = view.findViewById(R.id.crop_cb);

        mSyteManager = SDKApplication.getInstance().getSyteManager();
        initViews();
    }

    @Override
    public void onStart() {
        super.onStart();
        mSyteManager.subscribe(this);
        try {
            mSyteManager.initSyte();
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
        mSyteManager.unsubscribe();
    }

    private void initViews() {
        mGetBoundsSyncButton.setOnClickListener(this);
        mGetBoundsAsyncButton.setOnClickListener(this);
        mFetchOffersForTheFirstBoundCheckBox.setChecked(true);
        mImageUrlEditText.setText("https://cdn-images.farfetch-contents.com/13/70/55/96/13705596_18130188_1000.jpg");
        mSKUEditText.setText("13705596");
        mEnableCropCB.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        if (mEnableCropCB.isChecked()) {
            mSyteManager.setCoordinates(new CropCoordinates(
                    Double.parseDouble(mX1.getText().toString()),
                    Double.parseDouble(mY1.getText().toString()),
                    Double.parseDouble(mX2.getText().toString()),
                    Double.parseDouble(mY2.getText().toString())
            ));
        } else {
            mSyteManager.setCoordinates(null);
        }
        switch (v.getId()) {
            case R.id.bounds_sync_btn:
                mSyteManager.getBoundsSync(
                        mImageUrlEditText.getText().toString(),
                        mSKUEditText.getText().toString(),
                        mFetchOffersForTheFirstBoundCheckBox.isChecked()
                );
                break;
            case R.id.bounds_async_btn:
                mSyteManager.getBoundsAsync(
                        mImageUrlEditText.getText().toString(),
                        mSKUEditText.getText().toString(),
                        mFetchOffersForTheFirstBoundCheckBox.isChecked()
                );
                break;
        }
    }
}
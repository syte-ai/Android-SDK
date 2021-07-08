package com.syte.ai.android_sdk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.syte.ai.android_sdk.core.InitSyte;
import com.syte.ai.android_sdk.data.SimilarProductsRequestData;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.recommendation.SimilarProductsResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;


public class SimilarsFragment extends BaseFragment implements View.OnClickListener {

    private Button mGetSimilarsSync;
    private Button mGetSimilarsAsync;
    private EditText mImageUrlEditText;
    private EditText mSKUEditText;

    private RecommendationEngineClient mRecommendationEngineClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_similars, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetSimilarsSync = view.findViewById(R.id.similars_sync_btn);
        mGetSimilarsAsync = view.findViewById(R.id.similars_async_btn);
        mImageUrlEditText = view.findViewById(R.id.image_url_et);
        mSKUEditText = view.findViewById(R.id.sku_et);

        initViews();

        InitSyte initSyte = InitSyte.getInstance();
        SyteConfiguration syteConfiguration = null;
        try {
            syteConfiguration = new SyteConfiguration(
                    requireActivity(),
                    "9186",
                    "602e43d2d6ddcd558359f91f"
            );
        } catch (SyteInitializationException syteInitializationException) {
            syteInitializationException.printStackTrace();
        }
        try {
            initSyte.startSessionAsync(syteConfiguration, new SyteCallback<AccountDataService>() {
                @Override
                public void onResult(SyteResult<AccountDataService> syteResult) {
                    mRecommendationEngineClient = initSyte.retrieveRecommendationEngineClient();
                }
            });
        } catch (SyteInitializationException syteInitializationException) { }

    }

    private void initViews() {
        mGetSimilarsSync.setOnClickListener(this);
        mGetSimilarsAsync.setOnClickListener(this);
        mImageUrlEditText.setText("https://cdn-images.farfetch-contents.com/13/70/55/96/13705596_18130188_1000.jpg");
        mSKUEditText.setText("13705596");
    }

    @Override
    public void onClick(View v) {
        SimilarProductsRequestData similarProductsRequestData =
                new SimilarProductsRequestData(
                        mSKUEditText.getText().toString(),
                        mImageUrlEditText.getText().toString()
                );
        switch (v.getId()) {
            case R.id.similars_sync_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SDKApplication.getInstance().getSyteManager().setSimilarProductsResult(
                                mRecommendationEngineClient.getSimilarProducts(
                                        similarProductsRequestData
                                ).data
                        );
                        new Navigator(requireActivity().getSupportFragmentManager()).offersFragment();
                    }
                }).start();
                break;
            case R.id.similars_async_btn:
                mRecommendationEngineClient.getSimilarProductsAsync(
                        similarProductsRequestData,
                        new SyteCallback<SimilarProductsResult>() {
                            @Override
                            public void onResult(SyteResult<SimilarProductsResult> syteResult) {
                                SDKApplication.getInstance().getSyteManager().setSimilarProductsResult(
                                        syteResult.data
                                );
                                new Navigator(requireActivity().getSupportFragmentManager()).offersFragment();
                            }
                        }
                );
                break;
        }
    }
}
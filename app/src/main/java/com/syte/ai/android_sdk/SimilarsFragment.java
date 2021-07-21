package com.syte.ai.android_sdk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    private InitSyte mInitSyte;
    private EditText mLimitET;
    private EditText mUrlRefererET;

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
        mLimitET = view.findViewById(R.id.limit);
        mUrlRefererET = view.findViewById(R.id.url_referer);

        initViews();

        mInitSyte = InitSyte.getInstance();
        SyteConfiguration syteConfiguration = null;
        try {
            syteConfiguration = new SyteConfiguration(
                    requireActivity(),
                    "9186",
                    "602e43d2d6ddcd558359f91f"
            );
            syteConfiguration.setLocale(SDKApplication.getInstance().getLocale());
        } catch (SyteInitializationException syteInitializationException) {
            syteInitializationException.printStackTrace();
        }
        try {
            mInitSyte.startSessionAsync(syteConfiguration, new SyteCallback<AccountDataService>() {
                @Override
                public void onResult(SyteResult<AccountDataService> syteResult) {
                    mRecommendationEngineClient = mInitSyte.retrieveRecommendationEngineClient();
                }
            });
        } catch (SyteInitializationException syteInitializationException) { }

    }

    private void initViews() {
        mGetSimilarsSync.setOnClickListener(this);
        mGetSimilarsAsync.setOnClickListener(this);
        mImageUrlEditText.setText("https://cdn-images.farfetch-contents.com/13/70/55/96/13705596_18130188_1000.jpg");
        mSKUEditText.setText("13705596");
        mLimitET.setText("7");
        mUrlRefererET.setText("mobile_sdk");
    }

    @Override
    public void onClick(View v) {
        SimilarProductsRequestData similarProductsRequestData =
                new SimilarProductsRequestData(
                        mSKUEditText.getText().toString(),
                        mImageUrlEditText.getText().toString()
                );
        if (!SDKApplication.getInstance().getSessionSKUs().isEmpty()) {
            for (String sku : SDKApplication.getInstance().getSessionSKUs()) {
                mInitSyte.addViewedProduct(sku);
            }
            similarProductsRequestData.setPersonalizedRanking(true);
        }
        similarProductsRequestData.setFieldsToReturn(SDKApplication.getInstance().getRecommendationReturnField());
        similarProductsRequestData.setLimit(Integer.parseInt(mLimitET.getText().toString()));
        similarProductsRequestData.setSyteUrlReferer(mUrlRefererET.getText().toString());
        switch (v.getId()) {
            case R.id.similars_sync_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SyteResult<SimilarProductsResult> result = mRecommendationEngineClient.getSimilarProducts(
                                similarProductsRequestData
                        );
                        if (result.errorMessage != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), result.errorMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        SDKApplication.getInstance().getSyteManager().setSimilarProductsResult(
                                result.data
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
                                if (syteResult.errorMessage != null) {
                                    Toast.makeText(getActivity(), syteResult.errorMessage, Toast.LENGTH_LONG).show();
                                }
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
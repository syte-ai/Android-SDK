package com.syte.ai.android_sdk.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.syte.ai.android_sdk.RecommendationEngineClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.core.InitSyte;
import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;


public class PersonalizationFragment extends BaseFragment implements View.OnClickListener {

    private Button mPersonalizationSync;
    private Button mPersonalizationAsync;

    private RecommendationEngineClient mRecommendationEngineClient;
    private SyteConfiguration mSyteConfiguration;
    private InitSyte mInitSyte;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personalization, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPersonalizationSync = view.findViewById(R.id.personalization_sync_btn);
        mPersonalizationAsync = view.findViewById(R.id.personalization_async_btn);

        initViews();

        mInitSyte = InitSyte.getInstance();

        try {
            mSyteConfiguration = new SyteConfiguration(
                    requireActivity(),
                    "9186",
                    "602e43d2d6ddcd558359f91f"
            );
            mSyteConfiguration.setLocale(SDKApplication.getInstance().getLocale());
        } catch (SyteInitializationException syteInitializationException) {
            syteInitializationException.printStackTrace();
        }
        try {
            mInitSyte.startSessionAsync(mSyteConfiguration, new SyteCallback<AccountDataService>() {
                @Override
                public void onResult(SyteResult<AccountDataService> syteResult) {
                    mRecommendationEngineClient = mInitSyte.retrieveRecommendationEngineClient();
                }
            });
        } catch (SyteInitializationException syteInitializationException) { }

    }

    private void initViews() {
        mPersonalizationSync.setOnClickListener(this);
        mPersonalizationAsync.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        PersonalizationRequestData personalizationRequestData =
                new PersonalizationRequestData();
        for (String sku : SDKApplication.getInstance().getSessionSKUs()) {
            mInitSyte.addViewedProduct(sku);
        }
        personalizationRequestData.setFieldsToReturn(SDKApplication.getInstance().getRecommendationReturnField());
        switch (v.getId()) {
            case R.id.personalization_sync_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SyteResult<PersonalizationResult> result = mRecommendationEngineClient.getPersonalization(
                                personalizationRequestData
                        );
                        if (result.errorMessage != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), result.errorMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        SDKApplication.getInstance().getSyteManager().setPersonalizationResult(
                                result.data
                        );
                        new Navigator(requireActivity().getSupportFragmentManager()).offersFragment();
                    }
                }).start();
                break;
            case R.id.personalization_async_btn:
                mRecommendationEngineClient.getPersonalizationAsync(
                        personalizationRequestData,
                        new SyteCallback<PersonalizationResult>() {
                            @Override
                            public void onResult(SyteResult<PersonalizationResult> syteResult) {
                                if (syteResult.errorMessage != null) {
                                    Toast.makeText(getActivity(), syteResult.errorMessage, Toast.LENGTH_LONG).show();
                                }
                                SDKApplication.getInstance().getSyteManager().setPersonalizationResult(
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
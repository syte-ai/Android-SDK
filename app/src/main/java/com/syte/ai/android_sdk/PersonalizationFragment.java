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
import com.syte.ai.android_sdk.data.PersonalizationRequestData;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.recommendation.PersonalizationResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import java.util.Arrays;
import java.util.List;


public class PersonalizationFragment extends BaseFragment implements View.OnClickListener {

    private Button mPersonalizationSync;
    private Button mPersonalizationAsync;
    private EditText mSKUEditText;

    private RecommendationEngineClient mRecommendationEngineClient;
    private SyteConfiguration mSyteConfiguration;

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
        mSKUEditText = view.findViewById(R.id.sku_et);

        initViews();

        InitSyte initSyte = InitSyte.getInstance();

        try {
            mSyteConfiguration = new SyteConfiguration(
                    requireActivity(),
                    "9186",
                    "602e43d2d6ddcd558359f91f"
            );
        } catch (SyteInitializationException syteInitializationException) {
            syteInitializationException.printStackTrace();
        }
        try {
            initSyte.startSessionAsync(mSyteConfiguration, new SyteCallback<AccountDataService>() {
                @Override
                public void onResult(SyteResult<AccountDataService> syteResult) {
                    mRecommendationEngineClient = initSyte.retrieveRecommendationEngineClient();
                }
            });
        } catch (SyteInitializationException syteInitializationException) { }

    }

    private void initViews() {
        mPersonalizationSync.setOnClickListener(this);
        mPersonalizationAsync.setOnClickListener(this);
        mSKUEditText.setText("13705596,15126559");
    }

    @Override
    public void onClick(View v) {
        PersonalizationRequestData personalizationRequestData =
                new PersonalizationRequestData();
        List<String> sessionSkus = Arrays.asList(mSKUEditText.getText().toString().split(",").clone());
        mSyteConfiguration.addSessionSkus(sessionSkus);
        switch (v.getId()) {
            case R.id.personalization_sync_btn:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SDKApplication.getInstance().getSyteManager().setPersonalizationResult(
                                mRecommendationEngineClient.getPersonalization(
                                        personalizationRequestData
                                ).data
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
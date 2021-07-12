package com.syte.ai.android_sdk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.syte.ai.android_sdk.core.InitSyte;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.enums.RecommendationReturnField;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.events.EventCheckoutStart;
import com.syte.ai.android_sdk.events.Product;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConfigurationFragment extends BaseFragment {

    private EditText mLocaleET;
    private EditText mSessionSKUsET;
    private Button mConfigButton;
    private RecommendationReturnField mRecommendationReturnField = RecommendationReturnField.ALL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_configuration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLocaleET = view.findViewById(R.id.locale);
        mSessionSKUsET = view.findViewById(R.id.session_skus);
        mConfigButton = view.findViewById(R.id.config_btn);

        Spinner dropdown = view.findViewById(R.id.return_fields);

        String[] items = new String[]{
                RecommendationReturnField.ALL.name(),
                RecommendationReturnField.SKU.name(),
                RecommendationReturnField.IMAGE_URL.name(),
                RecommendationReturnField.IMAGE_URL_AND_SKU.name()};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRecommendationReturnField = RecommendationReturnField.valueOf(items[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mLocaleET.setText("en_US");
        mSessionSKUsET.setText("13705596,15126559");
        mConfigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDKApplication.getInstance().setLocale(mLocaleET.getText().toString());

                Set<String> set = new HashSet<>(Arrays.asList(mSessionSKUsET.getText().toString().split(",")));
                SDKApplication.getInstance().addSessionSKUs(set);
                SDKApplication.getInstance().setRecommendationReturnField(mRecommendationReturnField);
            }
        });
    }

}
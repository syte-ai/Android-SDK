package com.syte.ai.android_sdk.app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.app.Utils;
import com.syte.ai.android_sdk.app.adapter.PopularSearchAdapter;
import com.syte.ai.android_sdk.app.common.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConfigurationFragment extends BaseFragment {

    private EditText mLocaleET;
    private EditText mSessionSKUsET;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_configuration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLocaleET = view.findViewById(R.id.locale);
        mSessionSKUsET = view.findViewById(R.id.session_skus);
        Button configureButton = view.findViewById(R.id.config_btn);

        mLocaleET.setText(mSyteManager.getLocale());
        mSessionSKUsET.setText(Utils.viewedProductsString(mSyteManager.getViewedProducts()));
        configureButton.setOnClickListener(v -> {
            if (mLocaleET.getText().toString().isEmpty()) {
                showToast("WrongInput");
                return;
            }
            mSyteManager.setLocale(mLocaleET.getText().toString());
            if (!mSessionSKUsET.getText().toString().isEmpty()) {
                Set<String> set = new HashSet<>(Arrays.asList(mSessionSKUsET.getText().toString().split(",")));
                mSyteManager.addViewedProducts(set);
            }
            requireActivity().onBackPressed();
        });

        RecyclerView recyclerView = view.findViewById(R.id.search_history_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new PopularSearchAdapter(mSyteManager.getSearchHistory()));
    }

}
package com.syte.ai.android_sdk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends BaseFragment {

    private Button mInitButton;
    private Button mUrlSearchButton;
    private Button mWildSearchButton;
    private Navigator mNavigator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mNavigator = new Navigator(getActivity().getSupportFragmentManager());
        mInitButton = view.findViewById(R.id.init_btn);
        mInitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.initFragment();
            }
        });
        mUrlSearchButton = view.findViewById(R.id.url_search_btn);
        mUrlSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.urlimageSearchFragment();
            }
        });
        mWildSearchButton = view.findViewById(R.id.wild_search_btn);
        mWildSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.wildImageSearchFragment();
            }
        });
    }

}
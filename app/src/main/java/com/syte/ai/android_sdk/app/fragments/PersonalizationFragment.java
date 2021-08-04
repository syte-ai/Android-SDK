package com.syte.ai.android_sdk.app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.app.common.BaseFragment;
import com.syte.ai.android_sdk.data.PersonalizationRequestData;

import org.jetbrains.annotations.NotNull;


public class PersonalizationFragment extends BaseFragment implements View.OnClickListener {

    private Button mPersonalizationButton;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personalization, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPersonalizationButton = view.findViewById(R.id.personalization_btn);
        initViews();
    }

    private void initViews() {
        mPersonalizationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        PersonalizationRequestData personalizationRequestData =
                new PersonalizationRequestData();
        switch (v.getId()) {
            case R.id.personalization_btn:
                mSyteManager.personalization(
                        personalizationRequestData,
                        syteResult -> {
                           if (syteResult.isSuccessful) {
                               mSyteManager.setLastRetrievedItemsList(
                                       syteResult.data.getItems()
                               );
                               mNavigator.offersFragment();
                           }
                        });
                break;
        }
    }
}
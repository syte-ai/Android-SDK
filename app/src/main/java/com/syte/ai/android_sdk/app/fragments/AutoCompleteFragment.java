package com.syte.ai.android_sdk.app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.app.adapter.AutoCompleteTextAdapter;
import com.syte.ai.android_sdk.app.common.BaseFragment;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.auto_complete.AutoCompleteResult;

public class AutoCompleteFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auto_complete, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText textET = view.findViewById(R.id.text_input);
        RecyclerView recyclerView = view.findViewById(R.id.auto_complete_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final AutoCompleteTextAdapter[] adapter = {null};
        textET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String textString = String.valueOf(s);
                if (!textString.isEmpty()) {
                    mSyteManager.getAutoComplete(textString, syteResult -> {
                        if (adapter[0] == null) {
                            adapter[0] = new AutoCompleteTextAdapter(syteResult.data);
                            recyclerView.setAdapter(adapter[0]);
                        } else {
                            adapter[0].setData(syteResult.data);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}
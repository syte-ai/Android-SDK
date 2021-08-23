package com.syte.ai.android_sdk.app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.app.adapter.TextSearchAdapter;
import com.syte.ai.android_sdk.app.common.BaseFragment;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.text_search.TextSearchResult;

public class TextSearchFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText queryET = view.findViewById(R.id.query_et);
        Button searchBtn = view.findViewById(R.id.text_search_btn);
        RecyclerView recyclerView = view.findViewById(R.id.text_search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final TextSearchAdapter[] adapter = {null};
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSyteManager.getTextSearch(
                        queryET.getText().toString(),
                        syteResult -> {
                            if (adapter[0] == null) {
                                adapter[0] = new TextSearchAdapter(syteResult.data);
                                recyclerView.setAdapter(adapter[0]);
                            } else {
                                adapter[0].setData(syteResult.data);
                            }
                        });
            }
        });

    }

}
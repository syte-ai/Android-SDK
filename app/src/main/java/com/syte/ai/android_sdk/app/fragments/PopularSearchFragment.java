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

import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.app.adapter.PopularSearchAdapter;
import com.syte.ai.android_sdk.app.common.BaseFragment;

public class PopularSearchFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popular_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.popular_search_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSyteManager.getPopularSearch(syteResult -> {
            recyclerView.setAdapter(new PopularSearchAdapter(syteResult.data));
        });
    }
}
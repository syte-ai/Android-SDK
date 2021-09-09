package com.syte.ai.android_sdk.app.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.app.adapter.OffersAdapter;
import com.syte.ai.android_sdk.app.common.BaseFragment;
import com.syte.ai.android_sdk.data.result.offers.Item;

import java.util.List;

public class OffersFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.offers_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<Item> itemList = mSyteManager.getLastRetrievedItemsList();

        OffersAdapter adapter = new OffersAdapter(itemList);
        recyclerView.setAdapter(adapter);
    }

}
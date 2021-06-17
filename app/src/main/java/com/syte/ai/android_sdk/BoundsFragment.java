package com.syte.ai.android_sdk;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syte.ai.android_sdk.data.result.offers.Bound;
import com.syte.ai.android_sdk.data.result.offers.OffersResult;

public class BoundsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bounds, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        SDKApplication
                .getInstance()
                .getUrlImageSearchManager()
                .subscribe(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        SDKApplication
                .getInstance()
                .getUrlImageSearchManager()
                .unsubscribe();
    }

    public void onOffersRetrieved(OffersResult result) {
        new Navigator(getActivity().getSupportFragmentManager()).offersFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.offers_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BoundsAdapter adapter = new BoundsAdapter(
                SDKApplication
                        .getInstance()
                        .getUrlImageSearchManager()
                        .getBoundsResult()
                        .getBounds());
        adapter.setListener(new BoundsAdapter.ClickListener() {
            @Override
            public void onClick(Bound bound) {
                SDKApplication
                        .getInstance()
                        .getUrlImageSearchManager()
                        .getOffersAsync(bound);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
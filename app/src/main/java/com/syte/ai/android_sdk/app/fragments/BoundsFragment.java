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
import com.syte.ai.android_sdk.app.adapter.BoundsAdapter;
import com.syte.ai.android_sdk.app.common.BaseFragment;

public class BoundsFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bounds, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.offers_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        BoundsAdapter adapter = new BoundsAdapter(
                mSyteManager.getLastRetrievedBoundsList()
        );
        adapter.setListener(bound -> {
            mSyteManager.getOffers(
                    bound,
                    syteResult -> {
                        if (syteResult.isSuccessful) {
                            mSyteManager.setLastRetrievedItemsList(
                                    syteResult.data.getOffers()
                            );
                            mNavigator.offersFragment();
                        }
                    }
            );
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSyteManager.clearCachedBounds();
    }

}
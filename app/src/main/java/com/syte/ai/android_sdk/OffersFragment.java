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

import com.syte.ai.android_sdk.data.result.offers.Offer;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResponseItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OffersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offers, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.offers_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SyteManager syteManager = SDKApplication
                .getInstance()
                .getSyteManager();

        List<Offer> offerList = new ArrayList<>();

        if (syteManager.getLastRetrievedOffers() != null) {
            offerList = syteManager.getLastRetrievedOffers().getOffers();
        } else if (syteManager.getSimilarProductsResult() != null) {
            offerList = syteManager.getSimilarProductsResult().getSimilars();
        } else if (syteManager.getShopTheLookResult() != null) {
            for (ShopTheLookResponseItem item : syteManager.getShopTheLookResult().getItems()) {
                offerList.addAll(item.getOffers());
            }
        } else if (syteManager.getPersonalizationResult() != null) {
            offerList = syteManager.getPersonalizationResult().getItems();
        }

        OffersAdapter adapter = new OffersAdapter(offerList);
        recyclerView.setAdapter(adapter);
    }

}
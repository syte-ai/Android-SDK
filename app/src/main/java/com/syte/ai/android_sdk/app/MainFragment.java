package com.syte.ai.android_sdk.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.core.InitSyte;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;
import com.syte.ai.android_sdk.enums.Catalog;
import com.syte.ai.android_sdk.enums.EventsTag;
import com.syte.ai.android_sdk.enums.Placement;
import com.syte.ai.android_sdk.events.BaseSyteEvent;
import com.syte.ai.android_sdk.events.EventBBClick;
import com.syte.ai.android_sdk.events.EventBBShowLayout;
import com.syte.ai.android_sdk.events.EventBBShowResults;
import com.syte.ai.android_sdk.events.EventCameraButtonClick;
import com.syte.ai.android_sdk.events.EventCameraButtonImpression;
import com.syte.ai.android_sdk.events.EventCheckoutComplete;
import com.syte.ai.android_sdk.events.EventCheckoutStart;
import com.syte.ai.android_sdk.events.EventDiscoveryButtonClick;
import com.syte.ai.android_sdk.events.EventDiscoveryButtonImpression;
import com.syte.ai.android_sdk.events.EventOfferClick;
import com.syte.ai.android_sdk.events.EventPageView;
import com.syte.ai.android_sdk.events.EventProductsAddedToCart;
import com.syte.ai.android_sdk.events.EventShopTheLookOfferClick;
import com.syte.ai.android_sdk.events.EventShopTheLookShowLayout;
import com.syte.ai.android_sdk.events.EventSimilarItemsOfferClick;
import com.syte.ai.android_sdk.events.EventSimilarItemsShowLayout;
import com.syte.ai.android_sdk.events.Product;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

import java.util.Arrays;

public class MainFragment extends BaseFragment {

    private Button mInitButton;
    private Button mUrlSearchButton;
    private Button mWildSearchButton;
    private Button mGetSimilarsButton;
    private Button mGetShopTheLookButton;
    private Button mPersonalizationButton;
    private Button mConfigButton;
    private Button mFireEventsButton;
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
        mGetSimilarsButton = view.findViewById(R.id.similars);
        mGetSimilarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.similarsFragment();
            }
        });
        mGetShopTheLookButton = view.findViewById(R.id.ctl);
        mGetShopTheLookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.shopTheLookFragment();
            }
        });
        mPersonalizationButton = view.findViewById(R.id.personalization);
        mPersonalizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.personalizationFragment();
            }
        });
        mConfigButton = view.findViewById(R.id.config_btn);
        mConfigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigator.configFragment();
            }
        });
        mFireEventsButton = view.findViewById(R.id.events_btn);
        mFireEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InitSyte initSyte = InitSyte.newInstance();
                try {
                    initSyte.startSessionAsync(new SyteConfiguration(getActivity(), "9165", "601c206d0a7f780efb9360f3"), new SyteCallback<SytePlatformSettings>() {
                        @Override
                        public void onResult(SyteResult<SytePlatformSettings> syteResult) {
                            initSyte.fireEvent(
                                    new EventCheckoutStart(
                                            2,
                                            "UAH",
                                            Arrays.asList(new Product("test", 2, 2)),
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventBBClick(
                                            "url",
                                            "category",
                                            "gender",
                                            Catalog.GENERAL,
                                            "sdk-test")
                            );
                            initSyte.fireEvent(
                                    new EventBBShowLayout(
                                            "url",
                                            2,
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventBBShowResults(
                                            "url",
                                            "category",
                                            3,
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventCameraButtonClick(
                                            Placement.DEFAULT,
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventCameraButtonImpression(
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventCheckoutComplete(
                                            "1",
                                            2,
                                            "USD",
                                            Arrays.asList(new Product("test", 2, 2)),
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventDiscoveryButtonClick(
                                            "src",
                                            Placement.DEFAULT,
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventDiscoveryButtonImpression(
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventOfferClick(
                                            "sku",
                                            123,
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventPageView(
                                            "sku",
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventProductsAddedToCart(
                                            Arrays.asList(new Product("test", 2, 2)),
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventShopTheLookOfferClick(
                                            "sku",
                                            123,
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventShopTheLookShowLayout(
                                            3,
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventSimilarItemsOfferClick(
                                            "sku",
                                            1,
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(
                                    new EventSimilarItemsShowLayout(
                                            2,
                                            "sdk-test"
                                    )
                            );
                            initSyte.fireEvent(new BaseSyteEvent("custom_event", "sdk-test", EventsTag.SYTE_ANDROID_SDK) {
                                @Override
                                public String getRequestBodyString() {
                                    return null;
                                }
                            });
                        }
                    });
                } catch (SyteInitializationException syteInitializationException) {
                    syteInitializationException.printStackTrace();
                }
            }
        });
    }

}
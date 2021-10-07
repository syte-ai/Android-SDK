package com.syte.ai.android_sdk.app.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.syte.ai.android_sdk.app.Navigator;
import com.syte.ai.android_sdk.app.R;
import com.syte.ai.android_sdk.app.common.BaseFragment;
import com.syte.ai.android_sdk.core.Syte;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.enums.Catalog;
import com.syte.ai.android_sdk.enums.EventsTag;
import com.syte.ai.android_sdk.enums.Placement;
import com.syte.ai.android_sdk.enums.TextSearchEventType;
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
import com.syte.ai.android_sdk.events.EventTextShowResults;
import com.syte.ai.android_sdk.events.Product;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class MainFragment extends BaseFragment {

    private Button mUrlSearchButton;
    private Button mWildSearchButton;
    private Button mGetSimilarsButton;
    private Button mGetShopTheLookButton;
    private Button mPersonalizationButton;
    private Button mConfigButton;
    private Button mFireEventsButton;
    private Button mAutoCompleteButton;
    private Button mPopularSearchButton;
    private Button mTextSearchButton;
    private Navigator mNavigator;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mNavigator = new Navigator(requireActivity().getSupportFragmentManager());
        mUrlSearchButton = view.findViewById(R.id.url_search_btn);
        mUrlSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    mNavigator.urlimageSearchFragment();
                }
            }
        });
        mWildSearchButton = view.findViewById(R.id.wild_search_btn);
        mWildSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    mNavigator.wildImageSearchFragment();
                }
            }
        });
        mGetSimilarsButton = view.findViewById(R.id.similars);
        mGetSimilarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    mNavigator.similarsFragment();
                }
            }
        });
        mGetShopTheLookButton = view.findViewById(R.id.ctl);
        mGetShopTheLookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    mNavigator.shopTheLookFragment();
                }
            }
        });
        mPersonalizationButton = view.findViewById(R.id.personalization);
        mPersonalizationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    mNavigator.personalizationFragment();
                }
            }
        });
        mConfigButton = view.findViewById(R.id.config_btn);
        mConfigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    mNavigator.configFragment();
                }
            }
        });

        mAutoCompleteButton = view.findViewById(R.id.auto_complete_btn);
        mAutoCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    mNavigator.autoCompleteFragment();
                }
            }
        });

        mPopularSearchButton = view.findViewById(R.id.popular_search_btn);
        mPopularSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    mNavigator.popularSearchFragment();
                }
            }
        });

        mTextSearchButton = view.findViewById(R.id.text_search_btn);
        mTextSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    mNavigator.textSearchFragment();
                }
            }
        });

        mFireEventsButton = view.findViewById(R.id.events_btn);
        mFireEventsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() == 0) {
                    SyteConfiguration configuration = new SyteConfiguration(getActivity(), "9165", "601c206d0a7f780efb9360f3");
//                    configuration.enableLocalStorage(false);
                    Syte syte = Syte.newInstance(configuration);
                    syte.fireEvent(
                            new EventCheckoutStart(
                                    2,
                                    "UAH",
                                    Arrays.asList(new Product("test", 2, 2)),
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventBBClick(
                                    "url",
                                    "category",
                                    "gender",
                                    Catalog.GENERAL.getName(),
                                    "sdk-test")
                    );
                    syte.fireEvent(
                            new EventBBShowLayout(
                                    "url",
                                    2,
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventBBShowResults(
                                    "url",
                                    "category",
                                    3,
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventCameraButtonClick(
                                    Placement.DEFAULT,
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventCameraButtonImpression(
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventCheckoutComplete(
                                    "1",
                                    2,
                                    "USD",
                                    Arrays.asList(new Product("test", 2, 2)),
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventDiscoveryButtonClick(
                                    "src",
                                    Placement.DEFAULT,
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventDiscoveryButtonImpression(
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventOfferClick(
                                    "sku",
                                    123,
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventPageView(
                                    "PZZ70556-105",
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventProductsAddedToCart(
                                    Arrays.asList(new Product("test", 2, 2)),
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventShopTheLookOfferClick(
                                    "sku",
                                    123,
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventShopTheLookShowLayout(
                                    3,
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventSimilarItemsOfferClick(
                                    "sku",
                                    1,
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(
                            new EventSimilarItemsShowLayout(
                                    2,
                                    "sdk-test"
                            )
                    );
                    syte.fireEvent(new BaseSyteEvent("custom_event", "sdk-test", EventsTag.SYTE_ANDROID_SDK) {
                        @Override
                        public String getRequestBodyString() {
                            return null;
                        }
                    });
                    syte.fireEvent(
                            new EventTextShowResults(
                                    "text",
                                    TextSearchEventType.POPULAR_SEARCH,
                                    10,
                                    "sdk-test")
                    );
                }
            }
        });
    }

}
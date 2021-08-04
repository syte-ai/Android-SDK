package com.syte.ai.android_sdk.app;

import androidx.fragment.app.FragmentManager;

import com.syte.ai.android_sdk.app.fragments.BoundsFragment;
import com.syte.ai.android_sdk.app.fragments.ConfigurationFragment;
import com.syte.ai.android_sdk.app.fragments.MainFragment;
import com.syte.ai.android_sdk.app.fragments.OffersFragment;
import com.syte.ai.android_sdk.app.fragments.PersonalizationFragment;
import com.syte.ai.android_sdk.app.fragments.ShopTheLookFragment;
import com.syte.ai.android_sdk.app.fragments.SimilarsFragment;
import com.syte.ai.android_sdk.app.fragments.UrlImageSearchFragment;
import com.syte.ai.android_sdk.app.fragments.WildImageSearchFragment;

public class Navigator {

    private final FragmentManager mFragmentManager;

    public Navigator(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void urlimageSearchFragment() {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, new UrlImageSearchFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    public void wildImageSearchFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new WildImageSearchFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    public void mainFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new MainFragment(), null)
                .commit();
    }

    public void boundsFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new BoundsFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    public void offersFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new OffersFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    public void similarsFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new SimilarsFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    public void shopTheLookFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new ShopTheLookFragment(), null)
                .addToBackStack(null)
                .commit();
    }

    public void personalizationFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new PersonalizationFragment(), null)
                .addToBackStack(null)
                .commit();

    }

    public void configFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new ConfigurationFragment(), null)
                .addToBackStack(null)
                .commit();

    }

}

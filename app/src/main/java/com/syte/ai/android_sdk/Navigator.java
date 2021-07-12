package com.syte.ai.android_sdk;

import androidx.fragment.app.FragmentManager;

public class Navigator {

    private final FragmentManager mFragmentManager;

    public Navigator(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void initFragment() {
        mFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragment_holder, new InitFragment(), null)
                .commit();
    }

    public void urlimageSearchFragment() {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, new UrlImageSearchFragment(), null)
                .commit();
    }

    public void wildImageSearchFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new WildImageSearchFragment(), null)
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
                .commit();
    }

    public void offersFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new OffersFragment(), null)
                .commit();
    }

    public void similarsFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new SimilarsFragment(), null)
                .commit();
    }

    public void shopTheLookFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new ShopTheLookFragment(), null)
                .commit();
    }

    public void personalizationFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new PersonalizationFragment(), null)
                .commit();

    }

    public void configFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new ConfigurationFragment(), null)
                .addToBackStack(null)
                .commit();

    }

}

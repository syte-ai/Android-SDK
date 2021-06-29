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
                .add(R.id.fragment_holder, new UrlImageSearchFragment(), null)
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
}

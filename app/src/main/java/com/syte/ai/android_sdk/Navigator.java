package com.syte.ai.android_sdk;

import androidx.fragment.app.FragmentManager;

public class Navigator {

    private FragmentManager mFragmentManager;

    public Navigator(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public void initFragment() {
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_holder, new InitFragment(), null)
                .commit();
    }

}

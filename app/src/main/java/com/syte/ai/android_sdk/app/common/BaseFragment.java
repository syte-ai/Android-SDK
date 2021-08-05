package com.syte.ai.android_sdk.app.common;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.syte.ai.android_sdk.app.Navigator;
import com.syte.ai.android_sdk.app.SDKApplication;
import com.syte.ai.android_sdk.app.SyteManager;

public class BaseFragment extends Fragment {

    protected SyteManager mSyteManager;
    protected Navigator mNavigator;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSyteManager = SDKApplication.getInstance().getSyteManager();
        mNavigator = new Navigator(requireActivity().getSupportFragmentManager());
    }

    public void showToast(@Nullable String error) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(
                        getActivity(),
                        error == null ? "Error during initialization" : error,
                        Toast.LENGTH_LONG
                ).show();

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSyteManager.clearCachedItems();
    }
}

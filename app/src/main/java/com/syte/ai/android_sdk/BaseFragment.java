package com.syte.ai.android_sdk;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

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

}

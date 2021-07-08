package com.syte.ai.android_sdk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.syte.ai.android_sdk.core.InitSyte;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;

public class InitFragment extends Fragment {

    private Button mInitSyncBtn;
    private Button mInitAsyncBtn;
    private EditText mAccountDataServiceEt;
    private TextView mUserCredentialsTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_init, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mInitSyncBtn = view.findViewById(R.id.init_sync_btn);
        mInitAsyncBtn = view.findViewById(R.id.init_async_btn);
        mAccountDataServiceEt = view.findViewById(R.id.account_data_service_et);
        mUserCredentialsTv = view.findViewById(R.id.user_creds_tv);

        try {
            SyteConfiguration syteConfiguration = new SyteConfiguration(
                            requireContext().getApplicationContext(),
                    "9186",
                    "602e43d2d6ddcd558359f91f"
            );
            mUserCredentialsTv.setText("UserId - " + syteConfiguration.getUserId() + "\nSessionId - " + syteConfiguration.getSessionId());
            InitSyte initSyte = InitSyte.getInstance();
            mInitSyncBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                SyteResult<AccountDataService> accountDataService = initSyte.startSession(syteConfiguration);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (accountDataService.data != null) {
                                            mAccountDataServiceEt.setText(accountDataService.data.toString());
                                        }
                                        Toast.makeText(getActivity(), "Successful - " + accountDataService.isSuccessful, Toast.LENGTH_LONG).show();
                                    }
                                });
                            } catch (Exception e) {

                            }
                        }
                    }).start();
                }
            });
            mInitAsyncBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        initSyte.startSessionAsync(syteConfiguration, new SyteCallback<AccountDataService>() {
                            @Override
                            public void onResult(SyteResult<AccountDataService> syteResult) {
                                if (syteResult.data != null) {
                                    mAccountDataServiceEt.setText(syteResult.data.toString());
                                }
                                Toast.makeText(getActivity(), "Successful - " + syteResult.isSuccessful, Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (SyteInitializationException e) {}
                }
            });

        } catch (SyteInitializationException e) {
            e.printStackTrace();
        }
    }
}
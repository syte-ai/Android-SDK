package com.syte.ai.android_sdk.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.SytePlatformSettings;

public class MainActivity extends AppCompatActivity {

    private Navigator mNavigator;
    private SyteManager mSyteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.version)).setText(BuildConfig.VERSION_NAME);
        mSyteManager = SDKApplication.getInstance().getSyteManager();
        mSyteManager.initialize(syteResult -> {
            if (syteResult.isSuccessful) {
                mNavigator = new Navigator(getSupportFragmentManager());
                mNavigator.mainFragment();
            }
        });
    }
}
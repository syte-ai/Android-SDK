package com.syte.ai.android_sdk.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.syte.ai.android_sdk.core.InitSyte;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.offers.Bound;

public class MainActivity extends AppCompatActivity {
    private Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SDKApplication.getInstance().getSyteManager().mActivity = this;
        mNavigator = new Navigator(getSupportFragmentManager());
        mNavigator.mainFragment();
    }
}
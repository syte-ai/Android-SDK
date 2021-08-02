package com.syte.ai.android_sdk.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

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
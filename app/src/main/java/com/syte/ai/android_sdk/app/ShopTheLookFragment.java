package com.syte.ai.android_sdk.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.syte.ai.android_sdk.RecommendationEngineClient;
import com.syte.ai.android_sdk.SyteCallback;
import com.syte.ai.android_sdk.core.InitSyte;
import com.syte.ai.android_sdk.data.ShopTheLookRequestData;
import com.syte.ai.android_sdk.core.SyteConfiguration;
import com.syte.ai.android_sdk.data.result.SyteResult;
import com.syte.ai.android_sdk.data.result.account.AccountDataService;
import com.syte.ai.android_sdk.data.result.recommendation.ShopTheLookResult;
import com.syte.ai.android_sdk.exceptions.SyteInitializationException;


public class ShopTheLookFragment extends BaseFragment implements View.OnClickListener {

    private Button mGetShopTheLookSync;
    private Button mGetShopTheLookAsync;
    private EditText mImageUrlEditText;
    private EditText mSKUEditText;
    private CheckBox mEnableZipCheckBox;
    private InitSyte mInitSyte;
    private EditText mLimitET;
    private EditText mUrlRefererET;

    private RecommendationEngineClient mRecommendationEngineClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shop_the_look, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGetShopTheLookSync = view.findViewById(R.id.ctl_sync);
        mGetShopTheLookAsync = view.findViewById(R.id.ctl_async);
        mImageUrlEditText = view.findViewById(R.id.image_url_et);
        mSKUEditText = view.findViewById(R.id.sku_et);
        mEnableZipCheckBox = view.findViewById(R.id.zip_cb);
        mLimitET = view.findViewById(R.id.limit_per_bound);
        mUrlRefererET = view.findViewById(R.id.url_referer);

        initViews();

        mInitSyte = InitSyte.getInstance();
        SyteConfiguration syteConfiguration = null;
        try {
            syteConfiguration = new SyteConfiguration(
                    requireActivity(),
                    "9165",
                    "601c206d0a7f780efb9360f3"
            );
            syteConfiguration.setLocale(SDKApplication.getInstance().getLocale());
        } catch (SyteInitializationException syteInitializationException) {
            syteInitializationException.printStackTrace();
        }
        try {
            mInitSyte.startSessionAsync(syteConfiguration, new SyteCallback<AccountDataService>() {
                @Override
                public void onResult(SyteResult<AccountDataService> syteResult) {
                    mRecommendationEngineClient = mInitSyte.retrieveRecommendationEngineClient();
                }
            });
        } catch (SyteInitializationException syteInitializationException) {}

    }

    private void initViews() {
        mGetShopTheLookSync.setOnClickListener(this);
        mGetShopTheLookAsync.setOnClickListener(this);
        mImageUrlEditText.setText("https://sytestorageeu.blob.core.windows.net/text-static-feeds/boohoo_direct/PZZ70556-105.jpg?se=2023-10-31T19%3A05%3A46Z&sp=r&sv=2018-03-28&sr=b&sig=DQe1/iuTzLpl/hZhMzmb5jJF8qw41GdNlREzZvunw4k%3D");
        mSKUEditText.setText("PZZ70556-105");
        mLimitET.setText("3");
        mUrlRefererET.setText("mobile_sdk");
    }

    @Override
    public void onClick(View v) {
        ShopTheLookRequestData shopTheLookRequestData =
                new ShopTheLookRequestData(
                        mSKUEditText.getText().toString(),
                        mImageUrlEditText.getText().toString()
                );
        if (!SDKApplication.getInstance().getSessionSKUs().isEmpty()) {
            for (String sku : SDKApplication.getInstance().getSessionSKUs()) {
                mInitSyte.addViewedProduct(sku);
            }
            shopTheLookRequestData.setPersonalizedRanking(true);
        }
        shopTheLookRequestData.setLimitPerBound(Integer.parseInt(mLimitET.getText().toString()));
        shopTheLookRequestData.setSyteUrlReferer(mUrlRefererET.getText().toString());
        shopTheLookRequestData.setFieldsToReturn(SDKApplication.getInstance().getRecommendationReturnField());
        SDKApplication.getInstance().getSyteManager().zipOffers(mEnableZipCheckBox.isChecked());
        switch (v.getId()) {
            case R.id.ctl_sync:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SyteResult<ShopTheLookResult> result = mRecommendationEngineClient.getShopTheLook(
                                shopTheLookRequestData
                        );
                        if (result.errorMessage != null) {
                            getActivity().runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getActivity(), result.errorMessage, Toast.LENGTH_LONG).show();
                                        }
                                    }
                            );
                        }
                        SDKApplication.getInstance().getSyteManager().setShopTheLookResult(
                                result.data
                        );
                        new Navigator(requireActivity().getSupportFragmentManager()).offersFragment();
                    }
                }).start();
                break;
            case R.id.ctl_async:
                mRecommendationEngineClient.getShopTheLookAsync(
                        shopTheLookRequestData,
                        new SyteCallback<ShopTheLookResult>() {
                            @Override
                            public void onResult(SyteResult<ShopTheLookResult> syteResult) {
                                if (syteResult.errorMessage != null) {
                                    Toast.makeText(getActivity(), syteResult.errorMessage, Toast.LENGTH_LONG).show();
                                }
                                SDKApplication.getInstance().getSyteManager().setShopTheLookResult(
                                        syteResult.data
                                );
                                new Navigator(requireActivity().getSupportFragmentManager()).offersFragment();
                            }
                        }
                );
                break;
        }
    }
}
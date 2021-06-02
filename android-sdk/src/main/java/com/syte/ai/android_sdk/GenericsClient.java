package com.syte.ai.android_sdk;

import com.syte.ai.android_sdk.data.result.SyteResult;

public interface GenericsClient {

    SyteResult notifyChangeLocale(String locale);

    SyteResult notifyLogOut();

    SyteResult notifyAddSkuPdp(String sku);

    SyteResult notifyChangeLocaleAsync(String locale, SyteCallback callback);

    SyteResult notifyLogOutAsync(SyteCallback callback);

    SyteResult notifyAddSkuPdpAsync(String sku, SyteCallback callback);

}

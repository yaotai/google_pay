package com.yaotai.google_pay.itf;

import android.content.Context;

import com.android.billingclient.api.BillingClient;
import com.yaotai.google_pay.query.CommonCallback;
import com.yaotai.google_pay.query.ProductInfo;

import java.util.List;

public interface PayResultCallBack {
    void initFail(String e);

    void pay_success(String  purchaseToken);

    void pay_fail(@BillingClient.BillingResponseCode int code);

    void consume(boolean isConsume);

    void isDebug(boolean debug);

    void queryProductInfoToGoogle(Context context, List<String> productIds, CommonCallback<List<ProductInfo>> commonCallback);

    void pay();
}

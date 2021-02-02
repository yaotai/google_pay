package com.yaotai.google_pay.main;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;

import com.yaotai.google_pay.itf.PayActionCall;
import com.yaotai.google_pay.utils.PayLog;

//消耗
public class ConsumeAsync {
    public void consume(BillingClient client, final Purchase purchase, final PayActionCall<Boolean> consumeSeccussCall) {
        ConsumeParams mConsumeParams = ConsumeParams.newBuilder()
                .setDeveloperPayload(purchase.getDeveloperPayload())
                .setPurchaseToken(purchase.getPurchaseToken()).build();
        client.consumeAsync(mConsumeParams, new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    PayLog.print("消耗商品成功 :" + purchase.getSku());
                    consumeSeccussCall.onCall(true);
                } else {
                    PayLog.print("消耗商品失败:" + purchase.getSku());
                    consumeSeccussCall.onCall(false);
                }
            }


        });
    }
}

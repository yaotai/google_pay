package com.yaotai.google_pay.main;

import android.app.Activity;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.yaotai.google_pay.itf.PayActionCall2;
import com.yaotai.google_pay.itf.PayActionCall3;
import com.yaotai.google_pay.utils.PayLog;


public class PayInit {
    private BillingClient billingClient=null;
    public void get(final Activity context, final PurchasesUpdatedListener listener, final PayActionCall3<BillingClient, Boolean,String> isAvailableCall ){
        billingClient=BillingClient.newBuilder(context).setListener(listener).enablePendingPurchases().build();
        if (billingClient!=null){
            billingClient.startConnection(new BillingClientStateListener() {
                @Override
                public void onBillingSetupFinished(BillingResult billingResult) {
                    PayLog.print("onBillingSetupFinished billingResult.getResponseCode()=="+billingResult.getResponseCode());
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        isAvailableCall.onCall(billingClient,true,"BillingClient.BillingResponseCode.OK");
                    }else{
                        isAvailableCall.onCall(billingClient,false,billingResult.getDebugMessage());
                        PayLog.print("onBillingSetupFinished=getDebugMessage=="+billingResult.getDebugMessage());
                    }

                }

                @Override
                public void onBillingServiceDisconnected() {
                    //  isAvailableCall.onCall(false);
                    PayLog.print("onBillingServiceDisconnected");
                }
            });

        }else {
            PayLog.print("billingClient=null");
        }

    }
}

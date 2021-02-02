package com.yaotai.google_pay.main;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.yaotai.google_pay.itf.PayActionCall;
import com.yaotai.google_pay.utils.PayLog;

import java.util.ArrayList;
import java.util.List;

public class PayquerySkuDetailsAsync {
    public void querySkuDetailsAsync(BillingClient billingClient, String productId, final PayActionCall<SkuDetails> skuDetailsPayActionCall){
        PayLog.print("开始商品id 查询=="+productId);
        List<String> skuList = new ArrayList<>();
        skuList.add(productId);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                         if (skuDetailsList==null){
                             PayLog.print("querySkuDetailsAsync=getResponseCode=="+billingResult.getResponseCode()+",skuDetailsList="+skuDetailsList);
                         }else {
                             PayLog.print("querySkuDetailsAsync=getResponseCode=="+billingResult.getResponseCode()+",skuDetailsList.size="+skuDetailsList.size());
                         }

                        // Process the result.
                        if(billingResult.getResponseCode()== BillingClient.BillingResponseCode.OK){
                            SkuDetails mSkuDetails=skuDetailsList.get(0);
                            PayLog.print("查询到对应商品=="+mSkuDetails.toString());
                            //根据商品id 查询
                            skuDetailsPayActionCall.onCall(mSkuDetails);
                        }else{
                            PayLog.print("查询不到对应商品，具体原因是："+billingResult.getDebugMessage());
                        }
                    }
                });
    }
}

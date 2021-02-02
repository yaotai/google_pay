package com.yaotai.google_pay.main;

import android.app.Activity;
import androidx.annotation.Nullable;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.yaotai.google_pay.db.GooglePayConsumeDb;
import com.yaotai.google_pay.itf.PayActionCall;
import com.yaotai.google_pay.itf.PayActionCall3;
import com.yaotai.google_pay.itf.PayResultCallBack;
import com.yaotai.google_pay.utils.PayLog;
import java.util.ArrayList;
import java.util.List;

public class GoolgePlayManager implements PurchasesUpdatedListener {
    // 混淆 -keep class com.android.vending.billing.**
    private static PayInit payInit;
    private static BillingClient mBillingClient;
    private static boolean isCanPurchare = false;
    private static PayResultCallBack payActionCall;
    private PayUpToService payUpToService;
    private GooglePayConsumeDb googlePayConsumeDb;
    private static class H {
        private static GoolgePlayManager manager = new GoolgePlayManager();
    }
    private GooglePayClient googlePayClient;
    public static GoolgePlayManager l() {
        return H.manager;
    }
    //开始购买流程

    public void doPurchase(final Activity context,final GooglePayClient googlePayClient, PayResultCallBack  call) {
        this.payActionCall=call;
        this.googlePayClient=googlePayClient;
        payActionCall = call;
        payInit = new PayInit();
        payUpToService = new PayUpToService();
        googlePayConsumeDb = new GooglePayConsumeDb(context);
        payUpToService.FixMissingDeal(context,googlePayConsumeDb);
          payInit.get(context,this, new PayActionCall3<BillingClient, Boolean,String>() {

            @Override
            public void onCall(BillingClient client, Boolean aBoolean,String reslut) {
                isCanPurchare = true;
                if (isCanPurchare&&aBoolean) {
                    mBillingClient=client;
                    PayLog.print("开始初始化支付");
                    PayquerySkuDetailsAsync mPayquerySkuDetailsAsync=new PayquerySkuDetailsAsync();
                    mPayquerySkuDetailsAsync.querySkuDetailsAsync(mBillingClient, googlePayClient.getSku(), new PayActionCall<SkuDetails>() {
                        @Override
                        public void onCall(SkuDetails skuDetails) {
                        launchBillingFlow(context,skuDetails);
                        }
                    });

                } else {
                    payActionCall.initFail(reslut);
                    PayLog.print("初始化支付失败,error:"+reslut);
                }
            }
        });
    }

    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> purchasesList) {
        if (billingResult.getResponseCode()== BillingClient.BillingResponseCode.OK) {
            PayLog.print("支付结果 billingResult.getResponseCode():"  +billingResult.getResponseCode());
            ConsumeAsync consumeAsync = new ConsumeAsync();
            if (purchasesList!=null&&purchasesList.size()>0){
                for (int i = 0; i < purchasesList.size(); i++) {
                    Purchase mPurchase = purchasesList.get(i);
                    PayLog.print("购买过的商品 sku:" + mPurchase.getSku());
                    //PayLog.print("购买应用商品回调 mPurchase:" + mPurchase.toString());
                    if (mPurchase.getSku().equals(googlePayClient.getSku())) {
                        PayLog.print("当前商品购买成功 purchaseToken:" + mPurchase.getPurchaseToken());
                        payActionCall.pay_success( mPurchase.getPurchaseToken());
                    }
                    consumeAsync.consume(mBillingClient, mPurchase, new PayActionCall<Boolean>() {
                        @Override
                        public void onCall(Boolean aBoolean) {
                            payActionCall.consume(aBoolean);
                        }
                    });
                }
            }else {
                PayLog.print("支付结果 没有购买信息返回，billingResult.getResponseCode():" + +billingResult.getResponseCode());
            }
        }else {

        }
        if (billingResult.getResponseCode()== BillingClient.BillingResponseCode.SERVICE_TIMEOUT) {
            payActionCall.pay_fail(billingResult.getResponseCode());
        }
    }


    private boolean canbePurchase = false;

    private void launchBillingFlow(Activity activity, SkuDetails sku) {
        PayLog.print("发起支付 sku：" + sku);
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSkuDetails(sku)
                .build();
        mBillingClient.launchBillingFlow(activity,flowParams);
    }

    public void querySkuDetailsAsync(final Activity context, final BillingClient client, final String procuctId) {
        canbePurchase = false;
        PayLog.print("查询应用内商品详情:" + procuctId);
        List<String> skuList = new ArrayList<>();
        skuList.add(procuctId);//商品ID
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        client.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> skuDetailsList) {
                PayLog.print("商品查询querySkuDetailsAsync billingResult.getResponseCode()：" + billingResult.getResponseCode());
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    SkuDetails mSkuDetails=null;
                    for (int i = 0; i < skuDetailsList.size(); i++) {
                        PayLog.print("存在的商品详情 SkuDetails " + i + " :" + skuDetailsList.get(i).toString());
                        if (procuctId.equals(skuDetailsList.get(i).getSku())) {
                            mSkuDetails=skuDetailsList.get(i);
                            canbePurchase = true;
                        }
                    }
                    isCanPurchare = canbePurchase;
                    if (isCanPurchare) {
                        PayLog.print("商品详情 可以被购买 canbePurchase：" + isCanPurchare);
                        launchBillingFlow(context, mSkuDetails);
                    } else {
                        PayLog.print("商品详情 不可被购买 canbePurchase：" + isCanPurchare);
                    }
                }else {

                }

            }
        });
    }



    public void setPayResultCallBack(PayResultCallBack mPayResultCallBack) {
        this.payActionCall = mPayResultCallBack;
    }
}


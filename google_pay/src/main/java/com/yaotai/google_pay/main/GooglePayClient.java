package com.yaotai.google_pay.main;

import android.app.Activity;
import android.content.Context;

import com.yaotai.google_pay.constant.Constants;
import com.yaotai.google_pay.itf.PayActionCall;
import com.yaotai.google_pay.itf.PayResultCallBack;
import com.yaotai.google_pay.query.CommonCallback;
import com.yaotai.google_pay.query.GooglePay;
import com.yaotai.google_pay.query.ProductInfo;
import com.yaotai.google_pay.query.SkuDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GooglePayClient implements PayResultCallBack {
    private Activity activity;
    private String sku;

    @Override
    public void isDebug(boolean debug) {
        Constants.isDebug=debug;
    }

    public GooglePayClient(Activity activity, String sku) {
        this.activity=activity;
        this.sku = sku;
    }

    @Override
    public void initFail(String e) {
        mPayReslutCall.initFail(e);
    }

    @Override
    public void pay_success(String  purchaseToken) {
        mPayReslutCall.pay_success(purchaseToken);
    }

    @Override
    public void pay_fail(int code) {
        mPayReslutCall.pay_fail(code);
    }

    @Override
    public void consume(boolean isConsume) {
        mPayReslutCall.consume(isConsume);
    }

    public String getSku() {
        return sku;
    }
    @Override
    public void pay() {
        GoolgePlayManager.l().doPurchase(activity,this,GooglePayClient.this);
    }

    public void setmPayReslutCall(PayReslutCall mPayReslutCall) {
        this.mPayReslutCall = mPayReslutCall;
    }

    public   PayReslutCall mPayReslutCall;
    public interface PayReslutCall{
        void initFail(String e);

        void pay_success(String  purchaseToken);

        void pay_fail(int code);

        void consume(boolean isConsume);
    }

    @Override
    public void queryProductInfoToGoogle(Context context, List<String> productIds, final CommonCallback<List<ProductInfo>> commonCallback) {
        try {
            GooglePay.getIns(context).queryProductInfo(productIds, new PayActionCall<Map<String, SkuDetails>>() {
                @Override
                public void onCall(Map<String, SkuDetails> details) {
                    //Log.e("debugGooglePay", "查询到商品信息 = " + details);
                    Collection<SkuDetails> values = details == null ? null : details.values();
                    Iterator<SkuDetails> it = values == null ? null : values.iterator();
                    if (it == null) {
                        if (commonCallback != null) {
                            commonCallback.call(null);
                        }
                        return;
                    }
                    List<ProductInfo> result = new ArrayList<>();
                    while (it.hasNext()) {
                        SkuDetails detail = it.next();
                        ProductInfo info = new ProductInfo();
                        info.setProductID(detail.getSku());
                        info.setLocalizedTitle(detail.getTitle());
                        info.setLocalizedDescription(detail.getDescription());
                        //货币单位从mPrice中提取USD$4.99
                        info.setCurrencySymbol(detail.getPrice_currency_code());
                        //价格从mPrice中提取USD$4.99
                        info.setPrice(detail.getToastPrice());
                        //Log.e("debugGooglePay", "查询到商品信息 info= " + info.toString());
                        result.add(info);
                    }
                    if (commonCallback != null) {
                       // PayLog.print("GooglePay 查询到的产品信息-" + JSON.toJSONString(result));
                        commonCallback.call(result);
                    }
                }
            });

        } catch (Exception e) {

        }
    }
}

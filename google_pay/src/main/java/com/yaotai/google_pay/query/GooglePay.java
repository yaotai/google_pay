package com.yaotai.google_pay.query;


import android.content.Context;

import com.yaotai.google_pay.itf.PayActionCall;
import com.yaotai.google_pay.utils.PayLog;

import java.util.List;
import java.util.Map;



/*
 * 编写：wenlong on 2017/10/23 16:40
 * 企业QQ： 2853239883
 * 钉钉：13430330686
 */
public class GooglePay {
    public static GooglePay ins;
    //google支付部分：
    // 声明属性The helper object
    private IabHelper mHelper;
    private String TAG = "MyLog1";
    /**
     * Google是否初始化成功：
     */
    boolean iap_is_ok = false;
    private static Context context;
    /**
     * Google支付需要的
     * 购买产品的id
     */
    static String purchaseId = "";
    // (arbitrary) request code for the purchase flow
    //购买请求回调requestcode
    static final int RC_REQUEST = 1001;
    //base64EncodedPublicKey是在Google开发者后台复制过来的：要集成的应用——>服务和API——>此应用的许可密钥（自己去复制）
    String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgWIRTrLYcDKd7YTLSfLDZcmR34UswN79BWdk1CCzT1uC5l9SjZphWWIpbD30uvLnXf9eTKiqAhK3NIEOc593307DsWn7X3PKbO2cHKiE6iW0EJabnb1TAvjxN8UZ5WWHc0JeGU5o7CVszD1UdbhrSiSyKFCvyXPlg65w0H + jLhDHLRSHTmk9vcBV1ouK839LW9WkMSpiYH1ZPJiE1mt7Wa / X0e5rouHQBaTn3aEVuPgn6xfpvYK + v0DEnKPdSU8eVwYZxVU7BHfWSOlT95Bu3tCRCrfb7kQK4srR0NYF79ISZ5oycIkfxo9Fbf2KOJ + 4GDUjh4ggEng1kQx2G0y7ywIDAQAB\n" +
            "\n";
    private String[] skuList = new String[]{"7_qiniu_na0_1", "7_qiniu_na0_12", "30_qiniu_na0_1", "30_qiniu_na0_12"};
    private List<String> finalskuList = null;

    public static GooglePay getIns(final Context mContext) {
        if (ins == null) {
            synchronized (GooglePay.class) {
                if (ins == null) {
                    context = mContext.getApplicationContext();
                    ins = new GooglePay();
                }
            }
        }
        return ins;
    }

    /**
     * 查询产品信息
     * @param productIds
     * @param listenner
     */
    public void queryProductInfo(final List<String> productIds, final PayActionCall<Map<String, SkuDetails>> listenner) {

//        if (!PackUtils.isGoogle(context)) {
//            if (listenner != null) {
//                listenner.onFinish(null);
//            }
//            return;
//        }
        // Create the helper, passing it our context and the public key to verify signatures with
        PayLog.print("GooglePay  Creating IAB helper.");
        mHelper = new IabHelper(context, base64EncodedPublicKey);
        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(false);
        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        PayLog.print("GooglePay  Starting setup.");
        if (!mHelper.mSetupDone) {
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                @Override
                public void onIabSetupFinished(IabResult result) {
                    PayLog.print("GooglePay"+"Setup finished. " + result);
                    if (!result.isSuccess()) {
                        // Oh noes, there was a problem.
                        if (listenner != null) {
                            listenner.onCall(null);
                        }
                        return;
                    }
                    // Have we been disposed of in the meantime? If so, quit.
                    if (mHelper == null) {
                        if (listenner != null) {
                            listenner.onCall(null);
                        }
                        return;
                    }
                    // IAB is fully set up. Now, let's get an inventory of stuff we own.
                    mHelper.queryInventoryAsync(true, productIds, new IabHelper.QueryInventoryFinishedListener() {
                        @Override
                        public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                            Map<String, SkuDetails> skuMap = inv == null ? null : inv.mSkuMap;
                            if (listenner != null) {
                                listenner.onCall(skuMap);
                            }
                        }
                    });
                }
            });
        } else {
            mHelper.queryInventoryAsync(true, productIds, new IabHelper.QueryInventoryFinishedListener() {
                @Override
                public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                    Map<String, SkuDetails> skuMap = inv == null ? null : inv.mSkuMap;
                    if (listenner != null) {
                        listenner.onCall(skuMap);
                    }
                }
            });
        }
    }









}

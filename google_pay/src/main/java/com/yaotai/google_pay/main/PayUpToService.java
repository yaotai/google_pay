package com.yaotai.google_pay.main;

import android.content.Context;


import com.yaotai.google_pay.bean.PayDbBean;
import com.yaotai.google_pay.db.GooglePayConsumeDb;
import com.yaotai.google_pay.itf.PayActionCall;
import com.yaotai.google_pay.utils.PayLog;
import com.yaotai.google_pay.utils.PayThreadUtils;

import java.util.List;



public class PayUpToService {
    private int uploadTime=0;

    //付款成功过后需要通知后台进行google商品的销毁，要不然会无法购买同一产品
    public void upToService(final Context context,final String sku, final String yourHttpParams, final PayActionCall<String> callBack) {
        GooglePayConsumeDb googlePayConsumeDb=new GooglePayConsumeDb(context);
        googlePayConsumeDb.msg_deleteSingle(sku,yourHttpParams);
      //  PayLog.print("orderId:" + sku);
       // PayLog.print("jsPayParams:" + yourHttpParams);
    }

    //处理google内购掉单问题
    public void FixMissingDeal(final Context context,final GooglePayConsumeDb googlePayConsumeDb) {
        PayThreadUtils.getIns().runSingleThread(new Runnable() {
            @Override
            public void run() {
                List<PayDbBean> missingList = googlePayConsumeDb.msg_selectAll();
                if (missingList != null && missingList.size() > 0) {
                   // PayLog.print("之前未消耗订单列表size:"+missingList.size());
                    for (int i = 0; i < missingList.size(); i++) {
                        upToService(context,missingList.get(i).getOrderId(), missingList.get(i).getParams(), null);
                    }
                } else {
                  //  PayLog.print("之前未消耗订单列表size:"+missingList.size());
                }
            }
        });
    }

    public void setUploadTime(int uploadTime) {
        this.uploadTime = uploadTime;
    }
}

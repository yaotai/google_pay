package com.yaotai.google_pay.utils;

import android.util.Log;
import com.yaotai.google_pay.constant.Constants;

public class PayLog {
    public static void print(String msg){
        if (Constants.isDebug){
            Log.i("PayLog:",msg);
        }
    }
}

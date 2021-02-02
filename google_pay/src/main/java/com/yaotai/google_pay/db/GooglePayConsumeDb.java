package com.yaotai.google_pay.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.yaotai.google_pay.bean.PayDbBean;
import com.yaotai.google_pay.constant.Constants;
import com.yaotai.google_pay.utils.MySharedPreferenceUtil;
import com.yaotai.google_pay.utils.PayLog;

import java.util.ArrayList;
import java.util.List;

/*
 * 编写：wenlong on 2017/7/25 14:02
 * 企业QQ： 2853239883
 * 钉钉：13430330686
 */
public class GooglePayConsumeDb extends SQLiteOpenHelper {

    private String TABLE_NAME = "GOOGLE_PLY_CONSUME";
    private String MSG_ID = "id";
    private String ORDER_ID = "orderId";
    private String PARAMS = "params";
    private static long startTime = 0;
    private Context context;


    public GooglePayConsumeDb(Context context) {
        super(context, MySharedPreferenceUtil.getString(context, Constants.YOUR_APP_NAME, "") + "_GOOGLE_PAY_CONSUME", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + MSG_ID
                + " INTEGER primary key autoincrement, "
                + ORDER_ID + " text, "
                + PARAMS + " text);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //增加操作
    public long msg_insert(String orderid, String params) {
        SQLiteDatabase db = this.getWritableDatabase();
        /* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put(ORDER_ID, orderid);
        cv.put(PARAMS, params);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

//    public void msg_check() {
//        startTime = System.currentTimeMillis();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db
//                .query(TABLE_NAME, null, null, null, null, null, null);
//        PayLog.print("数据长度是：" + cursor.getCount());
//        formData(cursor);
//        PayLog.print("查询" + "条数据需要时间=" + (System.currentTimeMillis() - startTime));
//    }


    private String selection[] = new String[]{ORDER_ID, PARAMS};


    public List<PayDbBean> msg_selectAll() {
        final SQLiteDatabase db = this.getReadableDatabase();
        startTime = System.currentTimeMillis();
        Cursor cursor = db.query(TABLE_NAME, selection, null, null, null, null, null);
        return formData(cursor);
    }


    public void msg_deleteSingle(final String orderid, final String params) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            db.delete(TABLE_NAME, ORDER_ID + " =? and " + PARAMS + " =?", new String[]{orderid, params});
        } catch (Exception e) {
           // PayLog.print("msg_deleteSingle 异常：" + e.getMessage());
        }

    }


    private static List<PayDbBean> formData(Cursor cursor) {
        if (cursor == null) return null;
        //PayLog.print("数据长度是：" + cursor.getCount());
        List<PayDbBean> list = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            list.add(new PayDbBean(cursor.getString(0), cursor.getString(1)));
        }
       // PayLog.print("查询全部数据需要时间4=" + (System.currentTimeMillis() - startTime));
        return list;
    }


    public void drapAllData() {
      //  PayLog.print("清除数据库=======================================");
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from " + TABLE_NAME);
    }

}

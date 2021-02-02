package com.yaotai.google_pay.utils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * store package Name
 *
 * @author Administrator
 */
public class MySharedPreferenceUtil {

    private static SharedPreferences prefer;
    private final static String PRENAME = "prefer_packa_name";
    public static MySharedPreferenceUtil mysharedpreferenceutil;
    private final static String DOOR_NUMBER = "door_number";

    public static boolean putString(Context context, String key, String value) {
        prefer = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        Editor editor = prefer.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    public static String getString(Context context, String key, String defaultValue) {
        prefer = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return prefer.getString(key, defaultValue);
    }


    public static boolean putBoolean(Context context, String key, boolean value) {
        prefer = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        Editor editor = prefer.edit();
        editor.putBoolean(key, value);
        System.out.println("保存： " + key + " " + value);
        return editor.commit();
    }

    public static boolean getBoolean(Context context, String key,
                                     boolean defaultValue) {
        prefer = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return prefer.getBoolean(key, defaultValue);
    }

    public static boolean putLong(Context context, String key, long value) {
        prefer = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        Editor editor = prefer.edit();
        editor.putLong(key, value);
        System.out.println("保存： " + key + " " + value);
        return editor.commit();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        prefer = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return prefer.getLong(key, defaultValue);
    }

    public static boolean putInt(Context context, String key, int value) {
        prefer = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        Editor editor = prefer.edit();
        editor.putInt(key, value);
        System.out.println("保存： " + key + " " + value);
        return editor.commit();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        prefer = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return prefer.getInt(key, defaultValue);
    }


}

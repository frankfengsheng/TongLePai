package com.cheng.retrofit20.client;

import android.util.Log;

/**
 * Created by wumengmeng on 2016/7/16/0016.
 */
public class HttpLog {
    private final static String KEY_TAG = "httplog";


    public static void requestUrl(String info) {
        if (info == null) {
            return;
        }
        Log.i("requestUrl", info);
    }

    public static void requestBody(String info) {
        if (info == null ) {
            return;
        }
        Log.i("requestBody", info);
    }

    public static void resultJson(String info) {
        if (info == null) {
            return;
        }
        Log.i("resultJson", info);
    }


    public static void i(String info) {
        if (info == null ) {
            return;
        }
        Log.i(KEY_TAG, info);
    }

    public static void d(String info) {
        if (info == null ) {
            return;
        }
        Log.d(KEY_TAG, info);
    }

    public static void e(String info) {
        if (info == null ) {
            return;
        }
        Log.e(KEY_TAG, info);
    }

    public static void w(String info) {
        if (info == null ) {
            return;
        }
        Log.w(KEY_TAG, info);
    }
}

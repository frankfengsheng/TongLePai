package com.cheng.retrofit20.client;

import android.content.Context;

import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by wumengmeng on 2016/7/16/0016.
 */
public abstract class HttpCommand<T> {
    public Retrofit retrofit;
    public Callback<T> mCallback;
    protected static final String KEY_JDATA = "jdata";

    public abstract void execute();

    public Retrofit getRetrofit(Context context) {
        Retrofit retrofit = RetrofitClient.getRetrofit(context);
        return retrofit;
    }

    public void setCallback(Callback<T> callback) {
        this.mCallback = callback;
    }


    public abstract void cancel();
}

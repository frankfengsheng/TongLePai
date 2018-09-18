package com.cheng.retrofit20.callbacks;

import android.util.Log;

import com.cheng.retrofit20.client.BaseHttpResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wumengmeng on 2016/8/7/0007.
 */
public abstract class BaseCallback<T extends BaseHttpResult> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        Log.e("onResponse", "---code = " + response.code() + "----" + response.isSuccessful());
        if (response.code() == 200 && response.isSuccessful()) {


            int code = response.body().getStatus();

            if (code == 1 || code == 21 || code == 22 || code == 5 || code == 17 || code == 14 || code == 19 || code == 40|| code == 48) {
                if (code == 17) {
                    onLogin();
                } else {
                    onSuccess(response);
                }
            } else {
                onFailed(response.body().getMsg(), code);
            }

        } else {
            onFailed("请求失败", -1000);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e("onResponse", "---error = " + t.getMessage());
        onFailed("请求失败", -1001);
    }

    public abstract void onSuccess(Response<T> response);

    public abstract void onFailed(String msg, int code);

    public abstract void onLogin();
}

package com.cheng.retrofit20.http;

import android.content.Context;
import android.util.Log;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.client.RetrofitClient;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

/**
 * Created by wumengmeng on 2017/8/9/0009.
 */

public class BaseHttpCmdJava extends BaseHttpCmd {
    public BaseHttpCmdJava(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    public Retrofit getRetrofit(Context context) {
        Retrofit retrofit = RetrofitClient.getRetrofit(context);
        return retrofit;
    }

    @Override
    public RequestParams getParams() {
        if (params == null)
            params = new RequestParams();
        if (!params.getParentParams().containsKey(KEY_JDATA))
            configureExtraParams(params);
        else
            Log.i("resultJson",params.getParentParams().get(KEY_JDATA));
        return params;
    }

    public RequestBody getBody() {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), getParams().getJson());
        return requestBody;
    }
}

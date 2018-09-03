package com.cheng.retrofit20.client;

import android.content.Context;
import android.text.TextUtils;

import com.cheng.retrofit20.ApiService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by wumengmeng on 2016/7/16/0016.
 */
public class BaseHttpCmdCps extends HttpCommand {
    protected Context context;
    private ApiService apiService;
    private RequestParams params;
    public static final String KEY_BODY = "body";

    public BaseHttpCmdCps(Context context, RequestParams params) {
        this.context = context;
        this.params = params;
    }

    @Override
    public void execute() {
        retrofit = getRetrofit(context);
        getCall().enqueue(mCallback);
    }

    public RequestParams getParams() {
        if (params == null)
            params = new RequestParams();
        configureExtraParams1(params);
        for (Map.Entry<String, Object> p : params.getChildParams().entrySet()) {
            HttpLog.resultJson(p.getValue().toString());
        }
        return params;
    }
    protected void configureExtraParams1(RequestParams params) {
        if (params.getChildParams().size() > 0) {
            for (Map.Entry<String, Object> p : params.getChildParams().entrySet()) {
                if(!TextUtils.isEmpty(p.getValue().toString())){
                    params.add(p.getKey(), p.getValue().toString());
                }
            }
        }
    }


    public ApiService getApiService() {
        apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    @Override
    public Retrofit getRetrofit(Context context) {
        Retrofit retrofit = RetrofitClient.getRetrofit(context);
        return retrofit;
    }

    protected Call<?> getCall() {
        return null;
    }

    public void cancel() {
        getCall().cancel();
    }
}
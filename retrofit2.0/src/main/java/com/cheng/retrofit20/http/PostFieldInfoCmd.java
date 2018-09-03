package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmdCps;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by qupengcheng on 2017/2/16.
 */
public class PostFieldInfoCmd extends BaseHttpCmdCps {
    public static final String BODY = "body";
    public static final String K_TOKEN = "token";
    private Context context;

    public PostFieldInfoCmd(Context context, RequestParams params) {
        super(context, params);
        this.context = context;
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().GetPostFieldInfo(getParams().getChildParams().get("body").toString());
    }
}

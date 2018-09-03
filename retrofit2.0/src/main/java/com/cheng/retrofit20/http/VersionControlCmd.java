package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/5/21.
 */

public class VersionControlCmd extends BaseHttpCmd {
    public static final String K_VERSION = "android_v";
//    public static final String K_IOS_VERSION = "ios_v";

    public VersionControlCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getVersionControl(getParams().getParentParams());
    }
}

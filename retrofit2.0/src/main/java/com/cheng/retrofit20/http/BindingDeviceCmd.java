package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/5/21.
 */

public class BindingDeviceCmd extends BaseHttpCmd {
    public static final String K_USER_ID = "userid";
    public static final String K_TOKEN = "token";
    public static final String K_INFO_ID = "info_id";
    public static final String K_ID_CODE = "idcode";
    public static final String K_DEVICE_CODE = "device_code";
    public static final String K_DEVICE_ID = "device_id";

    public BindingDeviceCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getBindingDeviceList(getParams().getParentParams());
    }
}

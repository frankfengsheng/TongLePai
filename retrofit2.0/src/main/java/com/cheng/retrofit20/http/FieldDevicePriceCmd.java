package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldDevicePriceCmd extends BaseHttpCmd {
    public static final String K_ID = "id";
    public static final String K_USER_ID = "userid";
    public static final String K_TOKEN = "token";
    public static final String K_YEAR = "year";
    public static final String K_MONTH = "month";

    public FieldDevicePriceCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getFieldDevicePrice(getParams().getParentParams());
    }
}

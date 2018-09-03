package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldSendSmsCmd extends BaseHttpCmd {
    public static final String K_COUNT = "tel";
    public static final String K_USEID = "userid";

    public FieldSendSmsCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getFieldSms(getParams().getParentParams());
    }
}

package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmdCps;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldAllIncomeCmd extends BaseHttpCmdCps {

    public static final String BODY = "body";

    public FieldAllIncomeCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getFieldAllIncome(getParams().getChildParams().get("body").toString());
    }
}

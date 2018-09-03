package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/5/21.
 */

public class InvestorUpdateTelCmd extends BaseHttpCmd {
    public static final String K_OLD_TLE = "oldtel";
    public static final String K_USER_ID = "userid";
    public static final String K_NEW_TEL = "newtel";
    public static final String K_CODE = "code";
    public static final String K_TOKEN = "token";

    public InvestorUpdateTelCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getInvestorUpdate(getParams().getParentParams());
    }
}

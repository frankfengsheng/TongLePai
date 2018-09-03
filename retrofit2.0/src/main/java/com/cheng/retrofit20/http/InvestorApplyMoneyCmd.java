package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/6/21.
 */

public class InvestorApplyMoneyCmd extends BaseHttpCmd {

    public static final String K_ID = "userid";
    public static final String K_MONEY = "money";
    public static final String K_TOKEN = "token";

    public InvestorApplyMoneyCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getInvestorApplyMoney(getParams().getParentParams());
    }
}

package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmdCps;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/5/21.
 */

public class InvestorAllIncomeCmd extends BaseHttpCmdCps {
//    public static final String K_PAGE = "page";
//    public static final String K_YEAR = "year";
//    public static final String K_MONTH = "month";
//    public static final String K_USER_ID = "userid";
    public static final String BODY = "body";

    public InvestorAllIncomeCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getInvestorAllIncome(getParams().getChildParams().get("body").toString());
    }
}

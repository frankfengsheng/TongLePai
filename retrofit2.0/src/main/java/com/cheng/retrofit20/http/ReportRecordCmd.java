package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/5/21.
 */

public class ReportRecordCmd extends BaseHttpCmd {
    public static final String K_PAGE = "page";
    public static final String K_USER_ID = "userid";
    public static final String K_TOKEN = "token";

    public ReportRecordCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getReportRecord(getParams().getParentParams());
    }
}

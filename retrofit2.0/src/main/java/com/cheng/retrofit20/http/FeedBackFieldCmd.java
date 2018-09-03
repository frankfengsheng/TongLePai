package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/5/21.
 */

public class FeedBackFieldCmd extends BaseHttpCmd {
    public static final String K_USER_ID = "userid";
    public static final String K_NAME = "name";
    public static final String K_TEL = "tel";
    public static final String K_TYPE = "type";
    public static final String K_DESC = "desc";
    public static final String K_LITPIC_1 = "litpic_1";
    public static final String K_LITPIC_2 = "litpic_2";
    public static final String K_LITPIC_3 = "litpic_3";
    public static final String K_LITPIC_4 = "litpic_4";
    public static final String K_TOKEN = "token";

    public FeedBackFieldCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getFieldFeedBack(getParams().getParentParams());
    }
}

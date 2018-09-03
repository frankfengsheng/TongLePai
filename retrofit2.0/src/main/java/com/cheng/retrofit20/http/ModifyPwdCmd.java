package com.cheng.retrofit20.http;

import android.content.Context;

import com.cheng.retrofit20.client.BaseHttpCmd;
import com.cheng.retrofit20.client.RequestParams;

import retrofit2.Call;

/**
 * Created by cheng on 2018/5/21.
 */

public class ModifyPwdCmd extends BaseHttpCmd {
    public static final String K_TEL = "tel";
    public static final String K_CODE = "code";
    public static final String K_PSW = "pwd";
    public static final String K_PSW_AGAIN = "confirm_pwd";


    public ModifyPwdCmd(Context context, RequestParams params) {
        super(context, params);
    }

    @Override
    protected Call<?> getCall() {
        return getApiService().getModifyPsw(getParams().getParentParams());
    }
}

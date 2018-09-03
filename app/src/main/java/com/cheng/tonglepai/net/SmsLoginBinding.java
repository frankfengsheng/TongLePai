package com.cheng.tonglepai.net;

import android.content.Context;

import com.cheng.retrofit20.client.IUiDataBinding;
import com.cheng.retrofit20.data.SmsLoginResult;
import com.cheng.tonglepai.data.SmsLoginData;


/**
 * Created by cheng on 2018/5/21.
 */

public class SmsLoginBinding implements IUiDataBinding<SmsLoginData,SmsLoginResult> {
    private SmsLoginResult mResult;
    private Context mContext;

    public SmsLoginBinding(SmsLoginResult result, Context context) {
        this.mResult = result;
        this.mContext = context;
    }

    @Override
    public SmsLoginData getUiData() {
        SmsLoginData data = new SmsLoginData();
        data.setTel(mResult.getTel());
        return data;
    }
}

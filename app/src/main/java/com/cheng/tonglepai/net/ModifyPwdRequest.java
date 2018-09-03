package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.http.ModifyPwdCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.tool.MyChooseToastDialog;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class ModifyPwdRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;
    private MyChooseToastDialog progressDialog;

    public ModifyPwdRequest(Context context) {
        this.mContext = context;
    }


    public void requestLogin(String phone, String sms, String psw, String pswAgain) {
        HttpCommand httpCmd = newHttpCommand(phone, sms, psw, pswAgain);
        httpCmd.execute();
    }

    private RequestParams getParams(String phone, String sms, String psw, String pswAgain) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(ModifyPwdCmd.K_TEL, phone);
        parameters.putParams(ModifyPwdCmd.K_CODE, sms);
        parameters.putParams(ModifyPwdCmd.K_PSW, psw);
        parameters.putParams(ModifyPwdCmd.K_PSW_AGAIN, pswAgain);
        return parameters;
    }

    private HttpCommand newHttpCommand(String phone, String sms, String psw, String pswAgain) {
        HttpCommand httpCmd = new ModifyPwdCmd(mContext, getParams(phone, sms, psw, pswAgain));
        httpCmd.setCallback(new BaseCallback<BaseHttpResult>() {
            @Override
            public void onSuccess(Response<BaseHttpResult> response) {

                if (null != mListener) {
                    mListener.onSuccess(response.body());
                }
            }

            @Override
            public void onFailed(String msg, int code) {
                if (null != mListener) {
                    mListener.onFailed(msg, code);
                }
            }

            @Override
            public void onLogin() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
        });
        return httpCmd;
    }
}

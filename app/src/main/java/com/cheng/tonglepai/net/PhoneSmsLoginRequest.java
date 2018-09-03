package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.SmsLoginResult;
import com.cheng.retrofit20.http.PhoneSmsLoginCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.SmsLoginData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class PhoneSmsLoginRequest extends BaseHttpRequest<SmsLoginData> {

    private Context mContext;

    public PhoneSmsLoginRequest(Context context) {
        this.mContext = context;
    }


    public void requestLogin(String userName, String phone, String sms, String shareCode) {
        HttpCommand httpCmd = newHttpCommand(userName, phone, sms, shareCode);
        httpCmd.execute();
    }

    private RequestParams getParams(String userName, String phone, String sms, String shareCode) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(PhoneSmsLoginCmd.K_TEL, phone);
        parameters.putParams(PhoneSmsLoginCmd.K_CODE, sms);
        parameters.putParams(PhoneSmsLoginCmd.K_USER_NAME, userName);
        parameters.putParams(PhoneSmsLoginCmd.K_SHARE_CODE, shareCode);
        return parameters;
    }

    private HttpCommand newHttpCommand(String userName, String phone, String sms, String shareCode) {
        HttpCommand httpCmd = new PhoneSmsLoginCmd(mContext, getParams(userName, phone, sms, shareCode));
        httpCmd.setCallback(new BaseCallback<SmsLoginResult>() {
            @Override
            public void onSuccess(Response<SmsLoginResult> response) {
                if(response.body().getStatus() == 21){
                    Toast.makeText(mContext,"新用户请先进行注册",Toast.LENGTH_LONG).show();
                    return;
                }
                if (null != mListener) {
                    mListener.onSuccess(new SmsLoginBinding(response.body(), mContext).getUiData());
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

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.SmsLoginResult;
import com.cheng.retrofit20.http.RegisterCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.SmsLoginData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class RegisterRequest extends BaseHttpRequest<SmsLoginData> {

    private Context mContext;

    public RegisterRequest(Context context) {
        this.mContext = context;
    }


    public void requestRegister(String userName, String phone, String sms, String shareCode,String psw) {
        HttpCommand httpCmd = newHttpCommand(userName, phone, sms, shareCode,psw);
        httpCmd.execute();
    }

    private RequestParams getParams(String userName, String phone, String sms, String shareCode,String psw) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(RegisterCmd.K_TEL, phone);
        parameters.putParams(RegisterCmd.K_CODE, sms);
        parameters.putParams(RegisterCmd.K_USER_NAME, userName);
        parameters.putParams(RegisterCmd.K_SHARE_CODE, shareCode);
        parameters.putParams(RegisterCmd.K_PSW, psw);
        return parameters;
    }

    private HttpCommand newHttpCommand(String userName, String phone, String sms, String shareCode,String psw) {
        HttpCommand httpCmd = new RegisterCmd(mContext, getParams(userName, phone, sms, shareCode,psw));
        httpCmd.setCallback(new BaseCallback<SmsLoginResult>() {
            @Override
            public void onSuccess(Response<SmsLoginResult> response) {
                if (response.body().getStatus() == 22) {
                    Toast.makeText(mContext, "用户已存在，直接登录", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
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

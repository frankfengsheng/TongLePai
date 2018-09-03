package com.cheng.tonglepai.net;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.SmsLoginResult;
import com.cheng.retrofit20.http.LoginCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.activity.RegisterActivity;
import com.cheng.tonglepai.data.SmsLoginData;
import com.cheng.tonglepai.tool.MyChooseToastDialog;
import com.cheng.tonglepai.tool.MyToast;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class LoginRequest extends BaseHttpRequest<SmsLoginData> {

    private Context mContext;
    private MyChooseToastDialog progressDialog;

    public LoginRequest(Context context) {
        this.mContext = context;
    }


    public void requestLogin(String phone, String sms) {
        HttpCommand httpCmd = newHttpCommand(phone, sms);
        httpCmd.execute();
    }

    private RequestParams getParams(String phone, String sms) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(LoginCmd.K_TEL, phone);
        parameters.putParams(LoginCmd.K_CODE, sms);
        return parameters;
    }

    private HttpCommand newHttpCommand(String phone, String sms) {
        HttpCommand httpCmd = new LoginCmd(mContext, getParams(phone, sms));
        httpCmd.setCallback(new BaseCallback<SmsLoginResult>() {
            @Override
            public void onSuccess(Response<SmsLoginResult> response) {
                if (response.body().getStatus() == 21) {
                    progressDialog = MyToast.showChooseDialog((Activity) mContext, "该手机号尚未注册", "",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    Intent intent = new Intent(mContext, RegisterActivity.class);
                                    mContext.startActivity(intent);
                                    progressDialog.dismiss();
                                }
                            });

                    return;
                }

                if (response.body().getStatus() == 26) {
                    Toast.makeText(mContext,"密码错误，请重新登录",Toast.LENGTH_LONG).show();
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

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.LoginoutCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class LoginoutRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public LoginoutRequest(Context context) {
        this.mContext = context;
    }


    public void requestLogout(String phone) {
        HttpCommand httpCmd = newHttpCommand(phone);
        httpCmd.execute();
    }

    private RequestParams getParams(String phone) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(LoginoutCmd.K_PHONE, phone);
        parameters.putParams(LoginoutCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        parameters.putParams(LoginoutCmd.K_USERID, HttpConfig.newInstance(mContext).getUserid());
        return parameters;
    }

    private HttpCommand newHttpCommand(String phone) {
        HttpCommand httpCmd = new LoginoutCmd(mContext, getParams(phone));
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

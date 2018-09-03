package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.UserInfoResult;
import com.cheng.retrofit20.http.UserInfoCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.UserInfoData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class UserInfoRequest extends BaseHttpRequest<UserInfoData> {

    private Context mContext;

    public UserInfoRequest(Context context) {
        this.mContext = context;
    }


    public void requestTest() {
        HttpCommand httpCmd = newHttpCommand();
        httpCmd.execute();
    }

    private RequestParams getParams() {
        RequestParams parameters = new RequestParams();
        parameters.putParams(UserInfoCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(UserInfoCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand() {
        HttpCommand httpCmd = new UserInfoCmd(mContext, getParams());
        httpCmd.setCallback(new BaseCallback<UserInfoResult>() {
            @Override
            public void onSuccess(Response<UserInfoResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new UserInfoBinding(response.body(), mContext).getUiData());
                }
            }

            @Override
            public void onFailed(String msg, int code) {
                Log.e("onResponse", "---error 得到的= " + code);
                mListener.onFailed(msg, code);
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

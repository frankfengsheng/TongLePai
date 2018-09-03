package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.UnCheckResonResult;
import com.cheng.retrofit20.http.CheckDownCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class CheckDownRequest extends BaseHttpRequest<UnCheckResonResult> {

    private Context mContext;

    public CheckDownRequest(Context context) {
        this.mContext = context;
    }


    public void requestApplyMoney(String userId, String storeId) {
        HttpCommand httpCmd = newHttpCommand(userId, storeId);
        httpCmd.execute();
    }

    private RequestParams getParams(String userId, String storeId) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(CheckDownCmd.K_USER_ID, userId);
        parameters.putParams(CheckDownCmd.K_STORE_INFO_ID, storeId);
        parameters.putParams(CheckDownCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String userId, String storeId) {
        HttpCommand httpCmd = new CheckDownCmd(mContext, getParams(userId, storeId));
        httpCmd.setCallback(new BaseCallback<UnCheckResonResult>() {
            @Override
            public void onSuccess(Response<UnCheckResonResult> response) {
                if (null != mListener) {
                    if (null != mListener)
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

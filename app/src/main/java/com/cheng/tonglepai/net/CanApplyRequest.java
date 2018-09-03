package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.CanApplyResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.CanApplyCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class CanApplyRequest extends BaseHttpRequest<CanApplyResult> {

    private Context mContext;

    public CanApplyRequest(Context context) {
        this.mContext = context;
    }


    public void requestCanApply() {
        HttpCommand httpCmd = newHttpCommand();
        httpCmd.execute();
    }

    private RequestParams getParams() {
        RequestParams parameters = new RequestParams();
        parameters.putParams(CanApplyCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(CanApplyCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand() {
        HttpCommand httpCmd = new CanApplyCmd(mContext, getParams());
        httpCmd.setCallback(new BaseCallback<CanApplyResult>() {
            @Override
            public void onSuccess(Response<CanApplyResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new CanApplyBinding(response.body(), mContext).getUiData());
                }
            }

            @Override
            public void onFailed(String msg, int code) {
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

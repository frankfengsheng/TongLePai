package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.ShareCodeResult;
import com.cheng.retrofit20.http.ShareCodeCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.ShareCodeData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class ShareCodeRequest extends BaseHttpRequest<ShareCodeData> {

    private Context mContext;

    public ShareCodeRequest(Context context) {
        this.mContext = context;
    }


    public void requestShareCode() {
        HttpCommand httpCmd = newHttpCommand();
        httpCmd.execute();
    }

    private RequestParams getParams() {
        RequestParams parameters = new RequestParams();
        parameters.putParams(ShareCodeCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(ShareCodeCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand() {
        HttpCommand httpCmd = new ShareCodeCmd(mContext, getParams());
        httpCmd.setCallback(new BaseCallback<ShareCodeResult>() {
            @Override
            public void onSuccess(Response<ShareCodeResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new ShareCodeBinding(response.body(), mContext).getUiData());
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

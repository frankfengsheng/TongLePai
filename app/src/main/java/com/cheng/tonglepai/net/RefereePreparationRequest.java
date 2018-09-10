package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.RefereePreparationCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class RefereePreparationRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public RefereePreparationRequest(Context context) {
        this.mContext = context;
    }


    public void requestRefereePreparation(String name, String tel, String city, String remarks) {
        HttpCommand httpCmd = newHttpCommand(name, tel, city, remarks);
        httpCmd.execute();
    }

    private RequestParams getParams(String name, String tel, String city, String remarks) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(RefereePreparationCmd.K_CITY, city);
        parameters.putParams(RefereePreparationCmd.K_NAME, name);
        parameters.putParams(RefereePreparationCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(RefereePreparationCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        parameters.putParams(RefereePreparationCmd.K_TEL, tel);
        parameters.putParams(RefereePreparationCmd.K_REMARKS, remarks);
        return parameters;
    }

    private HttpCommand newHttpCommand(String name, String tel, String city, String remarks) {
        HttpCommand httpCmd = new RefereePreparationCmd(mContext, getParams(name, tel, city, remarks));
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

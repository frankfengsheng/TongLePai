package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.MarkerUpdateTelCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class MarkerUpdateTelRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public MarkerUpdateTelRequest(Context context) {
        this.mContext = context;
    }


    public void requestMarkerUpdateTel(String newtel, String code) {
        HttpCommand httpCmd = newHttpCommand(newtel, code);
        httpCmd.execute();
    }

    private RequestParams getParams(String newtel, String code) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(MarkerUpdateTelCmd.K_CODE, code);
        parameters.putParams(MarkerUpdateTelCmd.K_NEW_TEL, newtel);
        parameters.putParams(MarkerUpdateTelCmd.K_OLD_TLE, HttpConfig.newInstance(mContext).getUserTel());
        parameters.putParams(MarkerUpdateTelCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(MarkerUpdateTelCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String newtel, String code) {
        HttpCommand httpCmd = new MarkerUpdateTelCmd(mContext, getParams(newtel, code));
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

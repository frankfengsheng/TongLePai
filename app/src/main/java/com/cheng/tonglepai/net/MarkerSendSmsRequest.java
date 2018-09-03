package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.MarkerSendSmsCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class MarkerSendSmsRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public MarkerSendSmsRequest(Context context) {
        this.mContext = context;
    }


    public void requestInvestorSendSms(String tel) {
        HttpCommand httpCmd = newHttpCommand(tel);
        httpCmd.execute();
    }

    private RequestParams getParams(String tel) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(MarkerSendSmsCmd.K_COUNT, tel);
        parameters.putParams(MarkerSendSmsCmd.K_USEID, HttpConfig.newInstance(mContext).getUserid());
        return parameters;
    }

    private HttpCommand newHttpCommand(String tel) {
        HttpCommand httpCmd = new MarkerSendSmsCmd(mContext, getParams(tel));
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

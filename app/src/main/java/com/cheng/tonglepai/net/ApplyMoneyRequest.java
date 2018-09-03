package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.PartnerApplyMoneyCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class ApplyMoneyRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public ApplyMoneyRequest(Context context) {
        this.mContext = context;
    }


    public void requestApplyMoney(String money) {
        HttpCommand httpCmd = newHttpCommand(money);
        httpCmd.execute();
    }

    private RequestParams getParams(String money) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(PartnerApplyMoneyCmd.K_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(PartnerApplyMoneyCmd.K_MONEY, money);
        parameters.putParams(PartnerApplyMoneyCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String money) {
        HttpCommand httpCmd = new PartnerApplyMoneyCmd(mContext, getParams(money));
        httpCmd.setCallback(new BaseCallback<BaseHttpResult>() {
            @Override
            public void onSuccess(Response<BaseHttpResult> response) {
                if (null != mListener) {
                    if (null != mListener)
                        mListener.onSuccess(response.body());
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

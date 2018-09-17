package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.AlipayResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.AlipricePayCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldAlipayRequest extends BaseHttpRequest<AlipayResult> {

    private Context mContext;

    public FieldAlipayRequest(Context context) {
        this.mContext = context;
    }


    public void requestFieldAlipay(String pricePays) {
        HttpCommand httpCmd = newHttpCommand(pricePays);
        httpCmd.execute();
    }

    private RequestParams getParams(String pricePays) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(AlipricePayCmd.K_PAYS, pricePays);
        parameters.putParams(AlipricePayCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(AlipricePayCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String pricePays) {
        HttpCommand httpCmd = new AlipricePayCmd(mContext, getParams(pricePays));
        httpCmd.setCallback(new BaseCallback<AlipayResult>() {
            @Override
            public void onSuccess(Response<AlipayResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new AlipayBinding(response.body(), mContext).getUiData());
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

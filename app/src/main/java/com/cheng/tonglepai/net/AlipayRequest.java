package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.AlipayResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.AlipayCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class AlipayRequest extends BaseHttpRequest<AlipayResult> {

    private Context mContext;

    public AlipayRequest(Context context) {
        this.mContext = context;
    }


    public void requestAlipay(String id, String orderNo, String total) {
        HttpCommand httpCmd = newHttpCommand(id, orderNo, total);
        httpCmd.execute();
    }

    private RequestParams getParams(String id, String orderNo, String total) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(AlipayCmd.K_ID, id);
        parameters.putParams(AlipayCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(AlipayCmd.K_ORDER_NUMBER, orderNo);
        parameters.putParams(AlipayCmd.K_TOTAL, total);
        parameters.putParams(AlipayCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String id, String orderNo, String total) {
        HttpCommand httpCmd = new AlipayCmd(mContext, getParams(id, orderNo, total));
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

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.AlipayResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.MarkerAlipayCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class MarkerAlipayRequest extends BaseHttpRequest<AlipayResult> {

    private Context mContext;

    public MarkerAlipayRequest(Context context) {
        this.mContext = context;
    }


    public void requestMarkerAlipay(String id, String orderNo, String total) {
        HttpCommand httpCmd = newHttpCommand(id, orderNo, total);
        httpCmd.execute();
    }

    private RequestParams getParams(String id, String orderNo, String total) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(MarkerAlipayCmd.K_ID, id);
        parameters.putParams(MarkerAlipayCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(MarkerAlipayCmd.K_ORDER_NUMBER, orderNo);
        parameters.putParams(MarkerAlipayCmd.K_TOTAL, total);
        parameters.putParams(MarkerAlipayCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String id, String orderNo, String total) {
        HttpCommand httpCmd = new MarkerAlipayCmd(mContext, getParams(id, orderNo, total));
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

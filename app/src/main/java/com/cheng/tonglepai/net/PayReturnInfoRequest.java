package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.PayReturnInfoResult;
import com.cheng.retrofit20.http.PayReturnInfoCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.PayReturnInfoData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class PayReturnInfoRequest extends BaseHttpRequest<PayReturnInfoData> {

    private Context mContext;

    public PayReturnInfoRequest(Context context) {
        this.mContext = context;
    }


    public void requestAllDevice(String orderNo, String id) {
        HttpCommand httpCmd = newHttpCommand(orderNo, id);
        httpCmd.execute();
    }

    private RequestParams getParams(String orderNo, String id) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(PayReturnInfoCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(PayReturnInfoCmd.K_ORDER_NUMBER, orderNo);
        parameters.putParams(PayReturnInfoCmd.K_ID, id);
        parameters.putParams(PayReturnInfoCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String orderNo, String id) {
        HttpCommand httpCmd = new PayReturnInfoCmd(mContext, getParams(orderNo, id));
        httpCmd.setCallback(new BaseCallback<PayReturnInfoResult>() {
            @Override
            public void onSuccess(Response<PayReturnInfoResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new PayReturnInfoBinding(response.body(), mContext).getUiData());
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

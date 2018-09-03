package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.PayReturnInfoResult;
import com.cheng.retrofit20.http.MarkerPayReturnInfoCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.PayReturnInfoData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class MarkerPayReturnInfoRequest extends BaseHttpRequest<PayReturnInfoData> {

    private Context mContext;

    public MarkerPayReturnInfoRequest(Context context) {
        this.mContext = context;
    }


    public void requestMarkerPayReturnInfo(String orderNo, String id) {
        HttpCommand httpCmd = newHttpCommand(orderNo, id);
        httpCmd.execute();
    }

    private RequestParams getParams(String orderNo, String id) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(MarkerPayReturnInfoCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(MarkerPayReturnInfoCmd.K_ORDER_NUMBER, orderNo);
        parameters.putParams(MarkerPayReturnInfoCmd.K_ID, id);
        parameters.putParams(MarkerPayReturnInfoCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String orderNo, String id) {
        HttpCommand httpCmd = new MarkerPayReturnInfoCmd(mContext, getParams(orderNo, id));
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

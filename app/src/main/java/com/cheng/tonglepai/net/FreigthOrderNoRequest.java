package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.OrderNoResult;
import com.cheng.retrofit20.http.FreightOrderNoCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.OrderNoData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/7/27.
 */

public class FreigthOrderNoRequest extends BaseHttpRequest<OrderNoData> {
    private Context mContext;

    public FreigthOrderNoRequest(Context context) {
        this.mContext=context;
    }


    public void requestFreigtOrderNo(String store_id) {
        HttpCommand httpCmd = newHttpCommand(store_id);
        httpCmd.execute();
    }

    private RequestParams getParams(String store_id) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(FreightOrderNoCmd.K_ID,store_id);
        parameters.putParams(FreightOrderNoCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        parameters.putParams(FreightOrderNoCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        return parameters;
    }

    private HttpCommand newHttpCommand(String userId) {
        HttpCommand httpCmd = new FreightOrderNoCmd(mContext, getParams(userId));
        httpCmd.setCallback(new BaseCallback<OrderNoResult>() {
            @Override
            public void onSuccess(Response<OrderNoResult> response) {
                if (null != mListener) {
                    if (null != mListener)
                        mListener.onSuccess(new OrderNoBinding(response.body(), mContext).getUiData());
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

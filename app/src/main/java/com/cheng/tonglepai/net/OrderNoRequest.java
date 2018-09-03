package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.OrderNoResult;
import com.cheng.retrofit20.http.OrderNoCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.OrderNoData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/7/27.
 */

public class OrderNoRequest extends BaseHttpRequest<OrderNoData> {
    private Context mContext;

    public OrderNoRequest(Context context) {
        this.mContext=context;
    }


    public void requestOrderNo(String store_id,String total) {
        HttpCommand httpCmd = newHttpCommand(store_id,total);
        httpCmd.execute();
    }

    private RequestParams getParams(String store_id,String total) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(OrderNoCmd.K_ID,store_id);
        parameters.putParams(OrderNoCmd.K_TOTAL,total);
        parameters.putParams(OrderNoCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        parameters.putParams(OrderNoCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        return parameters;
    }

    private HttpCommand newHttpCommand(String userId,String code) {
        HttpCommand httpCmd = new OrderNoCmd(mContext, getParams(userId,code));
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

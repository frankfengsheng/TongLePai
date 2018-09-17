package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.DeviceBillResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.MarkerDeviceBillCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.DeviceBillData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class MarkerDeviceBillRequest extends BaseHttpRequest<DeviceBillData> {

    private Context mContext;

    public MarkerDeviceBillRequest(Context context) {
        this.mContext=context;
    }


    public void requestFieldDevicePrice(String id,String month, String year,String page,String storeName) {
        HttpCommand httpCmd = newHttpCommand(id,month,year,page,storeName);
        httpCmd.execute();
    }

    private RequestParams getParams(String id,String month, String year,String page,String storeName) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(MarkerDeviceBillCmd.K_ID,id);
        parameters.putParams(MarkerDeviceBillCmd.K_MONTH,month);
        parameters.putParams(MarkerDeviceBillCmd.K_YEAR,year);
        parameters.putParams(MarkerDeviceBillCmd.K_PAGE,page);
        parameters.putParams(MarkerDeviceBillCmd.K_STORE_NAME,storeName);
        parameters.putParams(MarkerDeviceBillCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        parameters.putParams(MarkerDeviceBillCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        return parameters;
    }

    private HttpCommand newHttpCommand(String id,String month, String year,String page,String storeName) {
        HttpCommand httpCmd = new MarkerDeviceBillCmd(mContext, getParams(id,month,year,page,storeName));
        httpCmd.setCallback(new BaseCallback<DeviceBillResult>() {
            @Override
            
            public void onSuccess(Response<DeviceBillResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new DeviceBillBinding(response.body(), mContext).getUiData());
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

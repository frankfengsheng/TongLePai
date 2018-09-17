package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.DevicePriceResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.MarkerDevicePriceCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.DevicePriceData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class MarkerDevicePriceRequest extends BaseHttpRequest<DevicePriceData> {

    private Context mContext;

    public MarkerDevicePriceRequest(Context context) {
        this.mContext=context;
    }


    public void requestMarkerDevicePrice(String id,String month, String year) {
        HttpCommand httpCmd = newHttpCommand(id,month,year);
        httpCmd.execute();
    }

    private RequestParams getParams(String id,String month, String year) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(MarkerDevicePriceCmd.K_ID,id);
        parameters.putParams(MarkerDevicePriceCmd.K_MONTH,month);
        parameters.putParams(MarkerDevicePriceCmd.K_YEAR,year);
        parameters.putParams(MarkerDevicePriceCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        parameters.putParams(MarkerDevicePriceCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        return parameters;
    }

    private HttpCommand newHttpCommand(String id,String month, String year) {
        HttpCommand httpCmd = new MarkerDevicePriceCmd(mContext, getParams(id,month,year));
        httpCmd.setCallback(new BaseCallback<DevicePriceResult>() {
            @Override

            public void onSuccess(Response<DevicePriceResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new DevicePriceBinding(response.body(), mContext).getUiData());
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

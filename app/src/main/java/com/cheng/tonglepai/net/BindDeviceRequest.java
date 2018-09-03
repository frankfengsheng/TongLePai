package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.BindDeviceListResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.BindDeviceCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.BindDeviceData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class BindDeviceRequest extends BaseHttpRequest<List<BindDeviceData>> {

    private Context mContext;

    public BindDeviceRequest(Context context) {
        this.mContext = context;
    }


    public void requestBindDevice(String page) {
        HttpCommand httpCmd = newHttpCommand(page);
        httpCmd.execute();
    }

    private RequestParams getParams(String page) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(BindDeviceCmd.K_PAGE, page);
        parameters.putParams(BindDeviceCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(BindDeviceCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String page) {
        HttpCommand httpCmd = new BindDeviceCmd(mContext, getParams(page));
        httpCmd.setCallback(new BaseCallback<BindDeviceListResult>() {
            @Override
            public void onSuccess(Response<BindDeviceListResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new BindDeviceBinding(response.body(), mContext).getUiData());
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

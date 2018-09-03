package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.DeviceListResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.DeviceListCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.DeviceListData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class DeviceListRequest extends BaseHttpRequest<List<DeviceListData>> {

    private Context mContext;

    public DeviceListRequest(Context context) {
        this.mContext = context;
    }


    public void requestAllDevice() {
        HttpCommand httpCmd = newHttpCommand();
        httpCmd.execute();
    }

    private RequestParams getParams() {
        RequestParams parameters = new RequestParams();
        parameters.putParams(DeviceListCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        parameters.putParams(DeviceListCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        return parameters;
    }

    private HttpCommand newHttpCommand() {
        HttpCommand httpCmd = new DeviceListCmd(mContext, getParams());
        httpCmd.setCallback(new BaseCallback<DeviceListResult>() {
            @Override
            public void onSuccess(Response<DeviceListResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new GetDeviceListBinding(response.body(), mContext).getUiData());
                }
            }

            @Override
            public void onFailed(String msg, int code) {
                mListener.onFailed(msg, code);

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

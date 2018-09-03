package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.AllDeviceResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.ExceptionDeviceCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.ExceptionDeviceData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class ExceptionDeviceRequest extends BaseHttpRequest<List<ExceptionDeviceData>> {

    private Context mContext;

    public ExceptionDeviceRequest(Context context) {
        this.mContext=context;
    }


    public void requestExceptionDevice(String userId,String page) {
        HttpCommand httpCmd = newHttpCommand(userId,page);
        httpCmd.execute();
    }

    private RequestParams getParams(String userId,String page) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(ExceptionDeviceCmd.K_USER_ID,userId);
        parameters.putParams(ExceptionDeviceCmd.K_PAGE,page);
        parameters.putParams(ExceptionDeviceCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String userId,String page) {
        HttpCommand httpCmd = new ExceptionDeviceCmd(mContext, getParams(userId,page));
        httpCmd.setCallback(new BaseCallback<AllDeviceResult>() {
            @Override
            public void onSuccess(Response<AllDeviceResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new ExceptionDeviceBinding(response.body(), mContext).getUiData());
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

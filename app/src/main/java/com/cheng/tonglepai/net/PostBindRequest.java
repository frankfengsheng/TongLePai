package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.BindingDeviceCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class PostBindRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public PostBindRequest(Context context) {
        this.mContext = context;
    }


    public void requestPostBind(String infoid, String deviceid,String deviceCode,String idCode) {
        HttpCommand httpCmd = newHttpCommand(infoid, deviceid,deviceCode,idCode);
        httpCmd.execute();
    }

    private RequestParams getParams(String infoid, String deviceid,String deviceCode,String idCode) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(BindingDeviceCmd.K_DEVICE_CODE, deviceCode);
        parameters.putParams(BindingDeviceCmd.K_DEVICE_ID, deviceid);
        parameters.putParams(BindingDeviceCmd.K_INFO_ID, infoid);
        parameters.putParams(BindingDeviceCmd.K_ID_CODE, idCode);
        parameters.putParams(BindingDeviceCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        parameters.putParams(BindingDeviceCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        return parameters;
    }

    private HttpCommand newHttpCommand(String infoid, String deviceid,String deviceCode,String idCode) {
        HttpCommand httpCmd = new BindingDeviceCmd(mContext, getParams(infoid, deviceid,deviceCode,idCode));
        httpCmd.setCallback(new BaseCallback<BaseHttpResult>() {
            @Override
            public void onSuccess(Response<BaseHttpResult> response) {
                if (null != mListener) {
                    if (null != mListener)
                        mListener.onSuccess(response.body());
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

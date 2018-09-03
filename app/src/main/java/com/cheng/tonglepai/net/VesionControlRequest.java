package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.VersionControlResult;
import com.cheng.retrofit20.http.VersionControlCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.VersionControlData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/8/13.
 */

public class VesionControlRequest extends BaseHttpRequest<VersionControlData> {
    private Context mContext;

    public VesionControlRequest(Context context) {
        this.mContext = context;
    }


    public void requestVesionControl(String version) {
        HttpCommand httpCmd = newHttpCommand(version);
        httpCmd.execute();
    }

    private RequestParams getParams(String version) {
        RequestParams parameters = new RequestParams();
        Log.i("---",version);
        parameters.putParams(VersionControlCmd.K_VERSION, version);
//        parameters.putParams(VersionControlCmd.K_IOS_VERSION, "");
        return parameters;
    }

    private HttpCommand newHttpCommand(String version) {
        HttpCommand httpCmd = new VersionControlCmd(mContext, getParams(version));
        httpCmd.setCallback(new BaseCallback<VersionControlResult>() {
            @Override
            public void onSuccess(Response<VersionControlResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new VersionControlBinding(response.body(), mContext).getUiData());
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

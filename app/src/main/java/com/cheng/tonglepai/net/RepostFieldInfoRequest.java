package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.RepostFieldInfoCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;


/**
 * Created by qupengcheng on 2017/2/16.
 */
public class RepostFieldInfoRequest extends BaseHttpRequest<BaseHttpResult> {
    private Context mContext;

    public RepostFieldInfoRequest(Context mContext) {
        this.mContext = mContext;
    }

    public void requestRepostFieldInfo(String data) {
        HttpCommand httpCommand = newCommand(data);
        httpCommand.execute();
    }

    private RequestParams getParams(String data) {
        RequestParams params = new RequestParams();
        params.putParams(RepostFieldInfoCmd.BODY,data);
        params.putParams(RepostFieldInfoCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return params;
    }

    private HttpCommand newCommand(String data) {
        HttpCommand httpCommand = new RepostFieldInfoCmd(mContext, getParams(data));
        httpCommand.setCallback(new BaseCallback<BaseHttpResult>() {

            @Override
            public void onSuccess(Response<BaseHttpResult> response) {
                if (null != mListener)
                    mListener.onSuccess(response.body());
            }

            @Override
            public void onFailed(String msg, int code) {
                if (null != mListener)
                    mListener.onFailed(msg, code);
            }

            @Override
            public void onLogin() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
        });
        return httpCommand;
    }
}

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.UnpassFieldDetailResult;
import com.cheng.retrofit20.http.UnpassFieldDetailCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.UnpassFieldDetailData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class UnpassFieldDetailRequest extends BaseHttpRequest<UnpassFieldDetailData> {

    private Context mContext;

    public UnpassFieldDetailRequest(Context context) {
        this.mContext = context;
    }


    public void requestUnpassFieldDetail(String storeId) {
        HttpCommand httpCmd = newHttpCommand(storeId);
        httpCmd.execute();
    }

    private RequestParams getParams(String storeId) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(UnpassFieldDetailCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(UnpassFieldDetailCmd.K_STORE_INFO_ID, storeId);
        parameters.putParams(UnpassFieldDetailCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String storeId) {
        HttpCommand httpCmd = new UnpassFieldDetailCmd(mContext, getParams(storeId));
        httpCmd.setCallback(new BaseCallback<UnpassFieldDetailResult>() {
            @Override
            public void onSuccess(Response<UnpassFieldDetailResult> response) {
                if (null != mListener) {
                    if (null != mListener)
                        mListener.onSuccess(new UnpassFieldDetailBinding(response.body(), mContext).getUiData());
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

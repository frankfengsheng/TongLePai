package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HsaPostFieldResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.NeedMoneyFieldlCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.HasPostFieldData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class NeedMoneyRequest extends BaseHttpRequest<List<HasPostFieldData>> {

    private Context mContext;

    public NeedMoneyRequest(Context context) {
        this.mContext=context;
    }


    public void requestField(String userId,String page) {
        HttpCommand httpCmd = newHttpCommand(userId,page);
        httpCmd.execute();
    }

    private RequestParams getParams(String userId, String page) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(NeedMoneyFieldlCmd.K_PAGE,page);
        parameters.putParams(NeedMoneyFieldlCmd.K_USER_ID,userId);
        parameters.putParams(NeedMoneyFieldlCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String userId,String page) {
        HttpCommand httpCmd = new NeedMoneyFieldlCmd(mContext, getParams(userId,page));
        httpCmd.setCallback(new BaseCallback<HsaPostFieldResult>() {
            @Override
            public void onSuccess(Response<HsaPostFieldResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new HasPostFieldBinding(response.body(), mContext).getUiData());
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

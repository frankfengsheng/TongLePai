package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.ApplyDetailData;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.ApplyDetailCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.ApplyDetailUseData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class ApplyDetailRequest extends BaseHttpRequest<List<ApplyDetailUseData>> {

    private Context mContext;

    public ApplyDetailRequest(Context context) {
        this.mContext=context;
    }


    public void requestTest(String userId,String page) {
        HttpCommand httpCmd = newHttpCommand(userId,page);
        httpCmd.execute();
    }

    private RequestParams getParams(String userId, String page) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(ApplyDetailCmd.K_PAGE,page);
        parameters.putParams(ApplyDetailCmd.K_USER_ID,userId);
        parameters.putParams(ApplyDetailCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String userId,String page) {
        HttpCommand httpCmd = new ApplyDetailCmd(mContext, getParams(userId,page));
        httpCmd.setCallback(new BaseCallback<ApplyDetailData>() {
            @Override
            public void onSuccess(Response<ApplyDetailData> response) {
                if (null != mListener) {
                    mListener.onSuccess(new ApplyDetailBinding(response.body(), mContext).getUiData());
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

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.FeedBackCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class FeedBackRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public FeedBackRequest(Context context) {
        this.mContext = context;
    }


    public void requestFeedBack(String name, String tel, String type, String desc, String litpic_1, String litpic_2, String litpic_3, String litpic_4) {
        HttpCommand httpCmd = newHttpCommand(name, tel, type, desc, litpic_1, litpic_2, litpic_3, litpic_4);
        httpCmd.execute();
    }

    private RequestParams getParams(String name, String tel, String type, String desc, String litpic_1, String litpic_2, String litpic_3, String litpic_4) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(FeedBackCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(FeedBackCmd.K_NAME, name);
        parameters.putParams(FeedBackCmd.K_TEL, tel);
        parameters.putParams(FeedBackCmd.K_TYPE, type);
        parameters.putParams(FeedBackCmd.K_DESC, desc);
        parameters.putParams(FeedBackCmd.K_LITPIC_1, litpic_1);
        parameters.putParams(FeedBackCmd.K_LITPIC_2, litpic_2);
        parameters.putParams(FeedBackCmd.K_LITPIC_3, litpic_3);
        parameters.putParams(FeedBackCmd.K_LITPIC_4, litpic_4);
        parameters.putParams(FeedBackCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String name, String tel, String type, String desc, String litpic_1, String litpic_2, String litpic_3, String litpic_4) {
        HttpCommand httpCmd = new FeedBackCmd(mContext, getParams(name, tel, type, desc, litpic_1, litpic_2, litpic_3, litpic_4));
        httpCmd.setCallback(new BaseCallback<BaseHttpResult>() {
            @Override
            public void onSuccess(Response<BaseHttpResult> response) {
                if (null != mListener) {
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

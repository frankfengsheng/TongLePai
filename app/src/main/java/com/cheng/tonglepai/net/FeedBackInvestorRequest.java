package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.FeedBackInvoterCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class FeedBackInvestorRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public FeedBackInvestorRequest(Context context) {
        this.mContext = context;
    }


    public void requestFeedBack(String name, String tel, String type, String desc, String litpic_1, String litpic_2, String litpic_3, String litpic_4) {
        HttpCommand httpCmd = newHttpCommand(name, tel, type, desc, litpic_1, litpic_2, litpic_3, litpic_4);
        httpCmd.execute();
    }

    private RequestParams getParams(String name, String tel, String type, String desc, String litpic_1, String litpic_2, String litpic_3, String litpic_4) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(FeedBackInvoterCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(FeedBackInvoterCmd.K_NAME, name);
        parameters.putParams(FeedBackInvoterCmd.K_TEL, tel);
        parameters.putParams(FeedBackInvoterCmd.K_TYPE, type);
        parameters.putParams(FeedBackInvoterCmd.K_DESC, desc);
        parameters.putParams(FeedBackInvoterCmd.K_LITPIC_1, litpic_1);
        parameters.putParams(FeedBackInvoterCmd.K_LITPIC_2, litpic_2);
        parameters.putParams(FeedBackInvoterCmd.K_LITPIC_3, litpic_3);
        parameters.putParams(FeedBackInvoterCmd.K_LITPIC_4, litpic_4);
        parameters.putParams(FeedBackInvoterCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String name, String tel, String type, String desc, String litpic_1, String litpic_2, String litpic_3, String litpic_4) {
        HttpCommand httpCmd = new FeedBackInvoterCmd(mContext, getParams(name, tel, type, desc, litpic_1, litpic_2, litpic_3, litpic_4));
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

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.InvestorChooseCheckResult;
import com.cheng.retrofit20.http.ChooseCheckCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.InvestorChooseCheckData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class ChooseCheckRequest extends BaseHttpRequest<InvestorChooseCheckData> {

    private Context mContext;

    public ChooseCheckRequest(Context context) {
        this.mContext = context;
    }


    public void requestChooseCheck(String id) {
        HttpCommand httpCmd = newHttpCommand(id);
        httpCmd.execute();
    }

    private RequestParams getParams(String id) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(ChooseCheckCmd.K_ID, id);
        parameters.putParams(ChooseCheckCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(ChooseCheckCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String id) {
        HttpCommand httpCmd = new ChooseCheckCmd(mContext, getParams(id));
        httpCmd.setCallback(new BaseCallback<InvestorChooseCheckResult>() {
            @Override
            public void onSuccess(Response<InvestorChooseCheckResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new ChooseCheckBinding(response.body(), mContext).getUiData());
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

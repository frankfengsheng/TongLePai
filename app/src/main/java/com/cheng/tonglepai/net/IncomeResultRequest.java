package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.IncomeMainResult;
import com.cheng.retrofit20.http.IncomeMainCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.IncomeMainData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class IncomeResultRequest extends BaseHttpRequest<IncomeMainData> {

    private Context mContext;

    public IncomeResultRequest(Context context) {
        this.mContext = context;
    }


    public void requestIncomeResult(String tel) {
        HttpCommand httpCmd = newHttpCommand(tel);
        httpCmd.execute();
    }

    private RequestParams getParams(String tel) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(IncomeMainCmd.K_TEL,tel);
        return parameters;
    }

    private HttpCommand newHttpCommand(String tel) {
        HttpCommand httpCmd = new IncomeMainCmd(mContext, getParams(tel));
        httpCmd.setCallback(new BaseCallback<IncomeMainResult>() {
            @Override
            public void onSuccess(Response<IncomeMainResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new IncomeMainBinding(response.body(), mContext).getUiData());
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

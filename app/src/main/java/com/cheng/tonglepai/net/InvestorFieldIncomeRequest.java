package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.FieldIncomeResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.InvestorFieldIncomeCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.FieldIncomeData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class InvestorFieldIncomeRequest extends BaseHttpRequest<FieldIncomeData> {

    private Context mContext;

    public InvestorFieldIncomeRequest(Context context) {
        this.mContext = context;
    }


    public void requestInvestorFieldIncome(String storeId) {
        HttpCommand httpCmd = newHttpCommand(storeId);
        httpCmd.execute();
    }

    private RequestParams getParams(String storeId) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(InvestorFieldIncomeCmd.K_STORE_INFO_ID, storeId);
        parameters.putParams(InvestorFieldIncomeCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(InvestorFieldIncomeCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String storeId) {
        HttpCommand httpCmd = new InvestorFieldIncomeCmd(mContext, getParams(storeId));
        httpCmd.setCallback(new BaseCallback<FieldIncomeResult>() {
            @Override
            public void onSuccess(Response<FieldIncomeResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new FieldIncomeBinding(response.body(), mContext).getUiData());
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

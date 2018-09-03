package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.InvestorIncomeResult;
import com.cheng.retrofit20.http.InvestorIncomeCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.InvestorIncomeData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class InvestorIncomeRequest extends BaseHttpRequest<InvestorIncomeData> {

    private Context mContext;

    public InvestorIncomeRequest(Context context) {
        this.mContext = context;
    }


    public void requestInvestorIncome(String storeId) {
        HttpCommand httpCmd = newHttpCommand(storeId);
        httpCmd.execute();
    }

    private RequestParams getParams(String storeId) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(InvestorIncomeCmd.K_STORE_INFO_ID, storeId);
        parameters.putParams(InvestorIncomeCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(InvestorIncomeCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String storeId) {
        HttpCommand httpCmd = new InvestorIncomeCmd(mContext, getParams(storeId));
        httpCmd.setCallback(new BaseCallback<InvestorIncomeResult>() {
            @Override
            public void onSuccess(Response<InvestorIncomeResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new InvestorIncomeBinding(response.body(), mContext).getUiData());
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

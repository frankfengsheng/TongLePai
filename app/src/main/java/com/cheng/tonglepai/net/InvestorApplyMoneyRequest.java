package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.InvestorApplyMoneyCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class InvestorApplyMoneyRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public InvestorApplyMoneyRequest(Context context) {
        this.mContext = context;
    }


    public void requestInvestorApplyMoney(String money, String bank, String bankAccount, String realMoney) {
        HttpCommand httpCmd = newHttpCommand(money,bank,bankAccount,realMoney);
        httpCmd.execute();
    }

    private RequestParams getParams(String money, String bank, String bankAccount, String realMoney) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(InvestorApplyMoneyCmd.K_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(InvestorApplyMoneyCmd.K_MONEY, money);
        parameters.putParams(InvestorApplyMoneyCmd.K_BANK, bank);
        parameters.putParams(InvestorApplyMoneyCmd.K_BANK_ACCOUNT, bankAccount);
        parameters.putParams(InvestorApplyMoneyCmd.K_PRICE_REAL, realMoney);
        parameters.putParams(InvestorApplyMoneyCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String money, String bank, String bankAccount, String realMoney) {
        HttpCommand httpCmd = new InvestorApplyMoneyCmd(mContext, getParams(money,bank,bankAccount,realMoney));
        httpCmd.setCallback(new BaseCallback<BaseHttpResult>() {
            @Override
            public void onSuccess(Response<BaseHttpResult> response) {
                if (null != mListener) {
                    if (null != mListener)
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

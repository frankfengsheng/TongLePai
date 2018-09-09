package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.FieldApplyMoneyCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldApplyMoneyRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public FieldApplyMoneyRequest(Context context) {
        this.mContext = context;
    }


    public void requestFieldApplyMoney(String money, String bank, String bankAccount, String realMoney) {
        HttpCommand httpCmd = newHttpCommand(money, bank, bankAccount, realMoney);
        httpCmd.execute();
    }

    private RequestParams getParams(String money, String bank, String bankAccount, String realMoney) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(FieldApplyMoneyCmd.K_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(FieldApplyMoneyCmd.K_MONEY, money);
        parameters.putParams(FieldApplyMoneyCmd.K_BANK, bank);
        parameters.putParams(FieldApplyMoneyCmd.K_BANK_ACCOUNT, bankAccount);
        parameters.putParams(FieldApplyMoneyCmd.K_PRICE_REAL, realMoney);
        parameters.putParams(FieldApplyMoneyCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String money, String bank, String bankAccount, String realMoney) {
        HttpCommand httpCmd = new FieldApplyMoneyCmd(mContext, getParams(money, bank, bankAccount, realMoney));
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

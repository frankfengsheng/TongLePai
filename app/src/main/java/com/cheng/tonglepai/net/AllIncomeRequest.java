package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.InvestorAllIncomeResult;
import com.cheng.retrofit20.http.AllIncomeCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.InvestorAllIncomeData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class AllIncomeRequest extends BaseHttpRequest<InvestorAllIncomeData> {

    private Context mContext;

    public AllIncomeRequest(Context context) {
        this.mContext = context;
    }


    public void requestAllDevice(String data) {
        HttpCommand httpCmd = newHttpCommand(data);
        httpCmd.execute();
    }

    private RequestParams getParams(String data) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(AllIncomeCmd.BODY, data);
//        parameters.putParams(InvestorAllIncomeCmd.K_PAGE, page);
//        parameters.putParams(InvestorAllIncomeCmd.K_YEAR, year);
//        parameters.putParams(InvestorAllIncomeCmd.K_MONTH, month);
//        parameters.putParams(InvestorAllIncomeCmd.K_USER_ID, "426");
        return parameters;
    }

    private HttpCommand newHttpCommand(String data) {
        HttpCommand httpCmd = new AllIncomeCmd(mContext, getParams(data));
        httpCmd.setCallback(new BaseCallback<InvestorAllIncomeResult>() {
            @Override
            public void onSuccess(Response<InvestorAllIncomeResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new InvestorAllIncomeBinding(response.body(), mContext).getUiData());
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

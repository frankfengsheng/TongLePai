package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.InvestorAllIncomeResult;
import com.cheng.retrofit20.http.FieldAllIncomeCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.InvestorAllIncomeData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldAllIncomeRequest extends BaseHttpRequest<InvestorAllIncomeData> {

    private Context mContext;

    public FieldAllIncomeRequest(Context context) {
        this.mContext = context;
    }


    public void requestFieldAllDevice(String data) {
        HttpCommand httpCmd = newHttpCommand(data);
        httpCmd.execute();
    }

    private RequestParams getParams(String data) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(FieldAllIncomeCmd.BODY, data);
//        parameters.putParams(InvestorAllIncomeCmd.K_PAGE, page);
//        parameters.putParams(InvestorAllIncomeCmd.K_YEAR, year);
//        parameters.putParams(InvestorAllIncomeCmd.K_MONTH, month);
//        parameters.putParams(InvestorAllIncomeCmd.K_USER_ID, "426");
        return parameters;
    }

    private HttpCommand newHttpCommand(String data) {
        HttpCommand httpCmd = new FieldAllIncomeCmd(mContext, getParams(data));
        httpCmd.setCallback(new BaseCallback<InvestorAllIncomeResult>() {
            @Override
            public void onSuccess(Response<InvestorAllIncomeResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new InvestorAllIncomeBinding(response.body(), mContext).getUiData());
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

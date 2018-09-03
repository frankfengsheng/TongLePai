package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.ApplyDetailData;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.InvestorApplyDetailCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.ApplyDetailUseData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class InvestorApplyDetailRequest extends BaseHttpRequest<List<ApplyDetailUseData>> {

    private Context mContext;

    public InvestorApplyDetailRequest(Context context) {
        this.mContext = context;
    }


    public void requestInvestorApplyDetail(String page) {
        HttpCommand httpCmd = newHttpCommand(page);
        httpCmd.execute();
    }

    private RequestParams getParams(String page) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(InvestorApplyDetailCmd.K_PAGE, page);
        parameters.putParams(InvestorApplyDetailCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(InvestorApplyDetailCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String page) {
        HttpCommand httpCmd = new InvestorApplyDetailCmd(mContext, getParams(page));
        httpCmd.setCallback(new BaseCallback<ApplyDetailData>() {
            @Override
            public void onSuccess(Response<ApplyDetailData> response) {
                if (null != mListener) {
                    mListener.onSuccess(new InvestorApplyDetailBinding(response.body(), mContext).getUiData());
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

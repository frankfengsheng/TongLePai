package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.ApplyDetailData;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.AllApplyDetailCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.ApplyDetailUseData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class AllApplyDetailRequest extends BaseHttpRequest<List<ApplyDetailUseData>> {

    private Context mContext;

    public AllApplyDetailRequest(Context context) {
        this.mContext = context;
    }


    public void requestInvestorApplyDetail(String page, String where) {
        HttpCommand httpCmd = newHttpCommand(page, where);
        httpCmd.execute();
    }

    private RequestParams getParams(String page, String where) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(AllApplyDetailCmd.K_PAGE, page);
        parameters.putParams(AllApplyDetailCmd.K_WHERE, where);
        parameters.putParams(AllApplyDetailCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(AllApplyDetailCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String page, String where) {
        HttpCommand httpCmd = new AllApplyDetailCmd(mContext, getParams(page, where));
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

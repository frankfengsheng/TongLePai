package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.InvestorFieldListResult;
import com.cheng.retrofit20.http.InvestorFieldListCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.InvestorFieldListData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class InvestorFieldListRequest extends BaseHttpRequest<List<InvestorFieldListData>> {

    private Context mContext;

    public InvestorFieldListRequest(Context context) {
        this.mContext = context;
    }


    public void requestFieldList(String page) {
        HttpCommand httpCmd = newHttpCommand(page);
        httpCmd.execute();
    }

    private RequestParams getParams(String page) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(InvestorFieldListCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(InvestorFieldListCmd.K_PAGE, page);
        parameters.putParams(InvestorFieldListCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String page) {
        HttpCommand httpCmd = new InvestorFieldListCmd(mContext, getParams(page));
        httpCmd.setCallback(new BaseCallback<InvestorFieldListResult>() {
            @Override
            public void onSuccess(Response<InvestorFieldListResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new InverstorFieldListBinding(response.body(), mContext).getUiData());
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

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.InvestorAddviceResult;
import com.cheng.retrofit20.http.InvestorDeviceListCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.InvestorDeviceListData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class InvestorDeviceListRequest extends BaseHttpRequest<List<InvestorDeviceListData>> {

    private Context mContext;

    public InvestorDeviceListRequest(Context context) {
        this.mContext = context;
    }


    public void requestInvestorDeviceList(String page, String by, String search) {
        HttpCommand httpCmd = newHttpCommand(page, by, search);
        httpCmd.execute();
    }

    private RequestParams getParams(String page, String by, String search) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(InvestorDeviceListCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        parameters.putParams(InvestorDeviceListCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(InvestorDeviceListCmd.K_BY, by);
        parameters.putParams(InvestorDeviceListCmd.K_PAGE, page);
        parameters.putParams(InvestorDeviceListCmd.K_SEARCH, search);
        return parameters;
    }

    private HttpCommand newHttpCommand(String page, String by, String search) {
        HttpCommand httpCmd = new InvestorDeviceListCmd(mContext, getParams(page, by, search));
        httpCmd.setCallback(new BaseCallback<InvestorAddviceResult>() {
            @Override
            public void onSuccess(Response<InvestorAddviceResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new InvestorDeviceListBinding(response.body(), mContext).getUiData());
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

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.AllDeviceResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.AllDeviceCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.ExceptionDeviceData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class AllDeviceRequest extends BaseHttpRequest<List<ExceptionDeviceData>> {

    private Context mContext;

    public AllDeviceRequest(Context context) {
        this.mContext = context;
    }


    public void requestAllDevice(String userId, String page, String isIncome, String searchInfo) {
        HttpCommand httpCmd = newHttpCommand(userId, page, isIncome, searchInfo);
        httpCmd.execute();
    }

    private RequestParams getParams(String userId, String page, String isIncomee, String searchInfo) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(AllDeviceCmd.K_PAGE, page);
        parameters.putParams(AllDeviceCmd.K_USER_ID, userId);
        parameters.putParams(AllDeviceCmd.K_BY_INCOME, isIncomee);
        parameters.putParams(AllDeviceCmd.K_WHERE, searchInfo);
        parameters.putParams(AllDeviceCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String userId, String page, String isIncome, String searchInfo) {
        HttpCommand httpCmd = new AllDeviceCmd(mContext, getParams(userId, page, isIncome, searchInfo));
        httpCmd.setCallback(new BaseCallback<AllDeviceResult>() {
            @Override
            public void onSuccess(Response<AllDeviceResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new AllDeviceBinding(response.body(), mContext).getUiData());
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

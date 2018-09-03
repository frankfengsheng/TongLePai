package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.CheckokRecordsResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.CheckRecordsCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.CheckokRecordsData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class CheckRecordsRequest extends BaseHttpRequest<CheckokRecordsData> {

    private Context mContext;

    public CheckRecordsRequest(Context context) {
        this.mContext = context;
    }


    public void requestCheckRecords(String orderNo) {
        HttpCommand httpCmd = newHttpCommand(orderNo);
        httpCmd.execute();
    }

    private RequestParams getParams(String orderNo) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(CheckRecordsCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(CheckRecordsCmd.K_ORDER_NO, orderNo);
        parameters.putParams(CheckRecordsCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String orderNo) {
        HttpCommand httpCmd = new CheckRecordsCmd(mContext, getParams(orderNo));
        httpCmd.setCallback(new BaseCallback<CheckokRecordsResult>() {
            @Override
            public void onSuccess(Response<CheckokRecordsResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new CheckRecordsBinding(response.body(), mContext).getUiData());
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

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.CheckinfoBillResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.FieldCheckBillCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.CheckBillData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldCheckBillRequest extends BaseHttpRequest<List<CheckBillData>> {

    private Context mContext;

    public FieldCheckBillRequest(Context context) {
        this.mContext = context;
    }


    public void requestFieldCheckBill(String id, String page, String month, String year) {
        HttpCommand httpCmd = newHttpCommand(id, page, month, year);
        httpCmd.execute();
    }

    private RequestParams getParams(String id, String page, String month, String year) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(FieldCheckBillCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(FieldCheckBillCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        parameters.putParams(FieldCheckBillCmd.K_ID, id);
        parameters.putParams(FieldCheckBillCmd.K_PAGE, page);
        parameters.putParams(FieldCheckBillCmd.K_MONTH, month);
        parameters.putParams(FieldCheckBillCmd.K_YEAR, year);
        return parameters;
    }

    private HttpCommand newHttpCommand(String id, String page, String month, String year) {
        HttpCommand httpCmd = new FieldCheckBillCmd(mContext, getParams(id, page, month, year));
        httpCmd.setCallback(new BaseCallback<CheckinfoBillResult>() {
            @Override
            public void onSuccess(Response<CheckinfoBillResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new CheckBillBinding(response.body(), mContext).getUiData());
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

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.BusinessTypeResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.BusinessTypeCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.BusinessTypeData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class BusinessTypeRequest extends BaseHttpRequest<List<BusinessTypeData>> {

    private Context mContext;

    public BusinessTypeRequest(Context context) {
        this.mContext = context;
    }


    public void requestAllDevice() {
        HttpCommand httpCmd = newHttpCommand();
        httpCmd.execute();
    }

    private RequestParams getParams() {
        RequestParams parameters = new RequestParams();
        parameters.putParams(BusinessTypeCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(BusinessTypeCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand() {
        HttpCommand httpCmd = new BusinessTypeCmd(mContext, getParams());
        httpCmd.setCallback(new BaseCallback<BusinessTypeResult>() {
            @Override
            public void onSuccess(Response<BusinessTypeResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new BusinessTypeBinding(response.body(), mContext).getUiData());
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

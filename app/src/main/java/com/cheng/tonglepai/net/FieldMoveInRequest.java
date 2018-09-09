package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.BaseHttpResult;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.FieldMoveInCmd;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldMoveInRequest extends BaseHttpRequest<BaseHttpResult> {

    private Context mContext;

    public FieldMoveInRequest(Context context) {
        this.mContext = context;
    }


    public void requestFieldMoveIn(String id,String nums) {
        HttpCommand httpCmd = newHttpCommand(id,nums);
        httpCmd.execute();
    }

    private RequestParams getParams(String id, String nums) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(FieldMoveInCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(FieldMoveInCmd.K_ID, id);
        parameters.putParams(FieldMoveInCmd.K_NUMS, nums);
        parameters.putParams(FieldMoveInCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String id, String nums) {
        HttpCommand httpCmd = new FieldMoveInCmd(mContext, getParams(id, nums));
        httpCmd.setCallback(new BaseCallback<BaseHttpResult>() {
            @Override
            public void onSuccess(Response<BaseHttpResult> response) {
                if (null != mListener) {
                    if (null != mListener)
                        mListener.onSuccess(response.body());
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

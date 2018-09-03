package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.FieldListResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.FieldListByCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.FieldListData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldListYitouRequest extends BaseHttpRequest<List<FieldListData>> {

    private Context mContext;

    public FieldListYitouRequest(Context context) {
        this.mContext = context;
    }


    public void requestFieldListYitou(String page) {
        HttpCommand httpCmd = newHttpCommand(page);
        httpCmd.execute();
    }

    private RequestParams getParams(String page) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(FieldListByCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(FieldListByCmd.K_PAGE, page);
        parameters.putParams(FieldListByCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String page) {
        HttpCommand httpCmd = new FieldListByCmd(mContext, getParams(page));
        httpCmd.setCallback(new BaseCallback<FieldListResult>() {
            @Override
            public void onSuccess(Response<FieldListResult> response) {
                if (null != mListener) {
                    mListener.onSuccess(new FieldListBinding(response.body(), mContext).getUiData());
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

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.FieldListResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.FieldListCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.FieldListData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldListRequest extends BaseHttpRequest<List<FieldListData>> {

    private Context mContext;

    public FieldListRequest(Context context) {
        this.mContext = context;
    }


    public void requestFieldList(String byOrder,String page) {
        HttpCommand httpCmd = newHttpCommand(byOrder,page);
        httpCmd.execute();
    }

    private RequestParams getParams(String byOrder,String page) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(FieldListCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(FieldListCmd.K_BY, byOrder);
        parameters.putParams(FieldListCmd.K_PAGE, page);
        parameters.putParams(FieldListCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String byOrder,String page) {
        HttpCommand httpCmd = new FieldListCmd(mContext, getParams(byOrder,page));
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

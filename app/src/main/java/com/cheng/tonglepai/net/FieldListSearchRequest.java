package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.FieldListResult;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.http.FieldListSearchCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.FieldListData;

import java.util.List;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class FieldListSearchRequest extends BaseHttpRequest<List<FieldListData>> {

    private Context mContext;

    public FieldListSearchRequest(Context context) {
        this.mContext = context;
    }


    public void requestFieldList(String page, String province, String city, String distinct) {
        HttpCommand httpCmd = newHttpCommand(page, province, city, distinct);
        httpCmd.execute();
    }

    private RequestParams getParams(String page, String province, String city, String distinct) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(FieldListSearchCmd.K_PAGE, page);
        parameters.putParams(FieldListSearchCmd.K_PROVINCEE, province);
        parameters.putParams(FieldListSearchCmd.K_CITY, city);
        parameters.putParams(FieldListSearchCmd.K_DISTINCT, distinct);
        parameters.putParams(FieldListSearchCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        parameters.putParams(FieldListSearchCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        return parameters;
    }

    private HttpCommand newHttpCommand(String page, String province, String city, String distinct) {
        HttpCommand httpCmd = new FieldListSearchCmd(mContext, getParams(page, province, city, distinct));
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

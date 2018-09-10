package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.RefereeListResult;
import com.cheng.retrofit20.http.RefereeListCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.RefereeListData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/9/10.
 */

public class RefereeListRequest extends BaseHttpRequest<RefereeListData> {
    private Context mContext;

    public RefereeListRequest(Context mContext) {
        this.mContext = mContext;
    }

    public void requestRefereeList(String page,String screen) {
        HttpCommand httpCommand = newCommand(page,screen);
        httpCommand.execute();
    }

    private RequestParams getParams(String page,String screen) {
        RequestParams params = new RequestParams();
        params.putParams(RefereeListCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        params.putParams(RefereeListCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        params.putParams(RefereeListCmd.K_PAGE, page);
        params.putParams(RefereeListCmd.K_SCREEN, screen);
        return params;
    }

    private HttpCommand newCommand(String page,String screen) {
        HttpCommand httpCommand = new RefereeListCmd(mContext, getParams(page,screen));
        httpCommand.setCallback(new BaseCallback<RefereeListResult>() {

            @Override
            public void onSuccess(Response<RefereeListResult> response) {
                if (null != mListener)
                    mListener.onSuccess(new RefereeListBinding(response.body(), mContext).getUiData());
            }

            @Override
            public void onFailed(String msg, int code) {
                if (null != mListener)
                    mListener.onFailed(msg, code);
            }

            @Override
            public void onLogin() {
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }
        });
        return httpCommand;
    }
}

package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.data.HttpConfig;
import com.cheng.retrofit20.data.PostMoneyRecordResult;
import com.cheng.retrofit20.http.PostMoneyRecordCmd;
import com.cheng.tonglepai.activity.LoginActivity;
import com.cheng.tonglepai.data.PostMoneyRecordData;

import retrofit2.Response;

/**
 * Created by cheng on 2018/9/10.
 */

public class PostMoneyRecordRequest extends BaseHttpRequest<PostMoneyRecordData> {
    private Context mContext;

    public PostMoneyRecordRequest(Context mContext) {
        this.mContext = mContext;
    }

    public void requestPostMoneyRecord(String page) {
        HttpCommand httpCommand = newCommand(page);
        httpCommand.execute();
    }

    private RequestParams getParams(String page) {
        RequestParams params = new RequestParams();
        params.putParams(PostMoneyRecordCmd.K_TOKEN, HttpConfig.newInstance(mContext).getAccessToken());
        params.putParams(PostMoneyRecordCmd.K_USER_ID, HttpConfig.newInstance(mContext).getUserid());
        params.putParams(PostMoneyRecordCmd.K_PAGE, page);
        return params;
    }

    private HttpCommand newCommand(String page) {
        HttpCommand httpCommand = new PostMoneyRecordCmd(mContext, getParams(page));
        httpCommand.setCallback(new BaseCallback<PostMoneyRecordResult>() {

            @Override
            public void onSuccess(Response<PostMoneyRecordResult> response) {
                if (null != mListener)
                    mListener.onSuccess(new PostMoneyRecordBinding(response.body(), mContext).getUiData());
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

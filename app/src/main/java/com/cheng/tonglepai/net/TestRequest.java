package com.cheng.tonglepai.net;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.cheng.retrofit20.callbacks.BaseCallback;
import com.cheng.retrofit20.client.BaseHttpRequest;
import com.cheng.retrofit20.client.HttpCommand;
import com.cheng.retrofit20.client.RequestParams;
import com.cheng.retrofit20.http.TestCmd;
import com.cheng.retrofit20.data.TestData;
import com.cheng.tonglepai.activity.LoginActivity;

import retrofit2.Response;

/**
 * Created by cheng on 2018/5/21.
 */

public class TestRequest extends BaseHttpRequest<TestData> {

    private Context mContext;

    public TestRequest(Context context) {
        this.mContext=context;
    }


    public void requestTest(String tel) {
        HttpCommand httpCmd = newHttpCommand(tel);
        httpCmd.execute();
    }

    private RequestParams getParams(String tel) {
        RequestParams parameters = new RequestParams();
        parameters.putParams(TestCmd.K_COUNT,tel);
        return parameters;
    }

    private HttpCommand newHttpCommand(String tel) {
        HttpCommand httpCmd = new TestCmd(mContext, getParams(tel));
        httpCmd.setCallback(new BaseCallback<TestData>() {
            @Override
            public void onSuccess(Response<TestData> response) {

                if (null != mListener) {
                    mListener.onSuccess(new TestBinding(response.body(), mContext).getUiData());
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
